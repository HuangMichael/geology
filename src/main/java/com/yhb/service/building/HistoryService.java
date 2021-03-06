package com.yhb.service.building;

import com.yhb.dao.building.HistoryRepository;
import com.yhb.dao.mediaType.MediaTypeRepository;
import com.yhb.domain.history.History;
import com.yhb.domain.mediaType.MediaType;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.SessionUtil;
import com.yhb.utils.StringUtils;
import com.yhb.utils.ThumbNailUtil;
import com.yhb.utils.UploadUtil;
import com.yhb.utils.ftp.FTPUploader;
import com.yhb.utils.ftp.FTPconfig;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by llm on 2017/5/16 0016.
 */
@Service
public class HistoryService extends BaseService {
    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    FTPconfig ftPconfig;

    /**
     * @param id
     * @param title
     * @param subTitle
     * @param historyDesc
     * @param parentId
     * @param status
     * @return 新增或者编辑：仅修改保存历史沿革的基本信息，在上传多媒体资料时才保存它的多媒体资料信息
     */
    public History save(Long id, String title, String subTitle, String historyDesc, Long parentId,
                        String status) {
        History history = new History();
        history.setId(id);
        history.setTitle(title);
        history.setSubTitle(subTitle);
        history.setHistoryDesc(historyDesc);
        history.setStatus(status);
        //计算父节点、等级和排序号
        History parent;
        Long level, sortNo;
        //如果id为null即新增记录，需要计算。如果id不为null，则为编辑记录
        if (id == null) {
            //前台没有选择父亲的时候，前台的父id设置为null，后台保存时level设置为0（最高等级），sortNo为同等级最大值加1
            if (parentId == null) {
                parent = null;
                level = 0L;
                sortNo = findMaxSortNoByLevel(0L) == null ? 1L : findMaxSortNoByLevel(0L) + 1;//如果MaxSortNo为null，则sortNo设置为1
                System.out.println("===新增===历史沿革基本信息保存=====parent==为null====sortNo====" + sortNo + "====level====" + level);
            } else {
                //前台选择了父亲的时候，获取该父亲节点，然后根据它求level。根据父id（parentId）查询孩子节点中最大的sortNo来求当前节点的sortNo
                parent = historyRepository.findOne(parentId);
                level = parent.getLevel() + 1;
                sortNo = findMaxSortNoByPId(parentId) == null ? 1L : findMaxSortNoByPId(parentId) + 1;
                System.out.println("===新增===历史沿革基本信息保存=====parent==不为null====sortNo====" + sortNo + "====level====" + level + "====parent====" + parent);
            }
            history.setParent(parent);
            history.setLevel(level);
            history.setSortNo(sortNo);
            System.out.println("===新增===历史沿革基本信息保存=====history====" + history);
        } else {
            History historyOld = historyRepository.findOne(id);

            history.setFileName(historyOld.getFileName());
            history.setFileSize(historyOld.getFileSize());
            history.setFileRelativeUrl(historyOld.getFileRelativeUrl());
            history.setFileAbsoluteUrl(historyOld.getFileAbsoluteUrl());
            history.setMediaType(historyOld.getMediaType());

            History parentOld = historyOld.getParent();
            //如果父亲节点改变了，则计算属性
            if ((parentOld == null && parentId != null) || (parentOld != null && (parentOld.getId() != parentId))) {//如果编辑时并没有改变父亲节点，则不需要修改它的父id和level、sortNo属性
                if (parentId == null) {
                    parent = null;
                    level = 0L;
                    sortNo = findMaxSortNoByLevel(0L) == null ? 1L : findMaxSortNoByLevel(0L) + 1;//如果MaxSortNo为null，则sortNo设置为1
                    System.out.println("===编辑===历史沿革基本信息保存=====parent==为null====sortNo====" + sortNo + "====level====" + level);
                } else {
                    //前台选择了父亲的时候，获取该父亲节点，然后根据它求level。根据父id（parentId）查询孩子节点中最大的sortNo来求当前节点的sortNo
                    parent = historyRepository.findOne(parentId);
                    level = parent.getLevel() + 1;
                    sortNo = findMaxSortNoByPId(parentId) == null ? 1L : findMaxSortNoByPId(parentId) + 1;
                    System.out.println("===编辑===历史沿革基本信息保存=====parent==不为null====sortNo====" + sortNo + "====level====" + level + "====parent====" + parent);
                }
            } else {//如果父亲节点没有改变，则使用原来的属性
                parent = historyOld.getParent();
                level = historyOld.getLevel();
                sortNo = historyOld.getSortNo();
            }
            history.setParent(parent);
            history.setLevel(level);
            history.setSortNo(sortNo);
            System.out.println("===编辑===历史沿革基本信息保存=====history====" + history);
        }
        return historyRepository.save(history);
    }

    /**
     * @param historyList 批量删除
     */
    public void deleteInBatch(List historyList) {
        historyRepository.deleteInBatch(historyList);
    }

    /**
     * @param id 根据ID删除历史沿革信息
     */
    public ReturnObject delete(Long id) {
        //从数据库中查询记录
        History history = historyRepository.findOne(id);
        if (history == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的历史沿革信息不存在！");
        } else {
            //如果该记录有子孙节点，则不能删除。如果没有子孙节点，则可以继续删除。
            List<History> children = historyRepository.findByParentIdOrderBySortNo(id);
            if (children.size() > 0) {
                System.out.println("======删除历史沿革多媒体文件id=====" + id + "========children==========" + children);
                return commonDataService.getReturnType(false, "", "id为" + id + "的历史沿革信息有关联信息，无法删除！");
            }

            //从数据库中删除该记录
            try {
                historyRepository.delete(history);
                History history1 = historyRepository.findOne(id);
                return commonDataService.getReturnType(history1 == null, "历史沿革信息删除成功！", "历史沿革信息删除失败!");
            } catch (Exception e) {
                return commonDataService.getReturnType(false, "历史沿革信息删除成功！", "历史沿革信息删除失败!");
            }
        }
    }

    /**
     * @return 查询所有的历史沿革信息
     */
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    /**
     * @param id
     * @return 根据id查询历史沿革信息
     */
    public History findById(Long id) {
        return historyRepository.findOne(id);
    }


    /**
     * @return 查询所有的id列表
     */
    public List<Long> selectAllIds() {
        return historyRepository.selectAllIds();
    }

    /**
     * @param file
     * @param request
     * @return 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     */
    @Transactional
    public ReturnObject uploadAndSave(MultipartFile file, Long historyId, HttpServletRequest request) {
        //首先查询是否存在historyId记录，不存在就返回null
        History history = historyRepository.findOne(historyId);
        if (history == null) {
            return commonDataService.getReturnType(false, "", "该历史沿革信息不存在，无法上传文件！");
        }
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String dirStr = "mediaDocs/history/" + timeStr;//历史沿革多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        if (!UploadUtil.createDirectory(realDir)) {//目录创建失败则返回null
            return commonDataService.getReturnType(false, "", "多媒体文件上传失败！");
        }
        String fileName = file.getOriginalFilename().replace(" ", "");//文件名
        String filePath = (realDir + "\\" + fileName).replace(" ", "");//绝对文件路径，去掉路径中的空格

        String type = file.getContentType();//获取文件类型
        Long mediaTypeId = 1L;//多媒体类型默认为文档
        if (type.contains("image")) {
            mediaTypeId = 2L;//多媒体类型 图片
        } else if (type.contains("video")) {
            mediaTypeId = 3L;//多媒体类型 视频
        }

        Boolean result = UploadUtil.uploadFile(file, filePath);//上传文件到Tomcat，作为临时文件
        log.info("upload file to tomcat----," + "filePath------" + filePath + ",result----------" + result);
        boolean resultFTP = FTPUploader.uploadFileToFtp(filePath, FTPconfig.IP, FTPconfig.PORT, FTPconfig.USERNAME, FTPconfig.PASSWORD, FTPconfig.ROOT, fileName, dirStr);
        if (resultFTP) {
            //ftp上传成功后删除Tomcat中的目录
            File existFile = new File(filePath);
            if (existFile.exists()) {
                existFile.delete();
            }

            //如果上传到ftp成功，就保存多媒体信息到数据库
            history.setFileName(fileName);
            history.setFileSize(file.getSize());
            String nginxUrl = ftPconfig.getFTPUrl() + "/" + dirStr + "/" + fileName;
            history.setFileRelativeUrl(nginxUrl);//设置文件的相对路径，"mediaDocs\\history"+文件名
            history.setFileAbsoluteUrl(nginxUrl);//设置文件的绝对路径，精确到盘符，如："E:\bmis0628\....\webapp"+"mediaDocs\\history"+文件名
            history.setMediaType(mediaTypeRepository.findOne(mediaTypeId));
            history.setStatus("1");//默认设置为1,为已审核状态，
            History history1 = historyRepository.save(history);
            return commonDataService.getReturnType(history1 != null, "多媒体文件上传成功！", "多媒体文件上传失败！");
        } else {
            return commonDataService.getReturnType(false, "", "多媒体文件上传失败！");
        }
    }

    /**
     * @param level
     * @return 根据级别查询历史沿革信息
     */
    public List<History> findByLevel(Long level) {
        return historyRepository.findByLevelOrderBySortNo(level);
    }

    /**
     * @return 根据pId查询所有符合的列表
     */
    public List<History> findByParentId(Long pId) {
        return historyRepository.findByParentIdOrderBySortNo(pId);
    }

    /**
     * @param pId 父id
     * @return 根据父id查询孩子节点中最大的sortNo，返回最大的排序值
     */
    public Long findMaxSortNoByPId(Long pId) {
        return historyRepository.findMaxSortNoByPId(pId);
    }

    /**
     * @param level 级别
     * @return 根据级别查询同级别里最大的sortNo，返回最大的排序值maxSortNo
     */
    public Long findMaxSortNoByLevel(Long level) {
        return historyRepository.findMaxSortNoByLevel(level);
    }

}
