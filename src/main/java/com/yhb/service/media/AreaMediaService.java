package com.yhb.service.media;

import com.yhb.dao.area.AreaMediaRepository;
import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.mediaTree.MediaTreeRepository;
import com.yhb.dao.mediaType.MediaTypeRepository;
import com.yhb.dao.user.UserRepository;
import com.yhb.domain.area.AreaMedia;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.SessionUtil;
import com.yhb.utils.StringUtils;
import com.yhb.utils.ThumbNailUtil;
import com.yhb.utils.UploadUtil;
import com.yhb.utils.ftp.FTPUploader;
import com.yhb.utils.ftp.FTPconfig;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 * 区块多媒体信息业务类
 */
@Service
public class AreaMediaService extends BaseService {
    @Autowired
    AreaMediaRepository areaMediaRepository;

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    MediaTreeRepository mediaTreeRepository;

    @Autowired
    FTPconfig ftPconfig;

    /**
     * @param areaMedia
     * @return 保存的区块多媒体信息
     */
    public AreaMedia save(AreaMedia areaMedia) {
        return areaMediaRepository.save(areaMedia);
    }

    /**
     * @param areaMediaList
     * @return 保存的区块多媒体信息列表
     */
    public List<AreaMedia> save(List<AreaMedia> areaMediaList) {
        return areaMediaRepository.save(areaMediaList);
    }

    /**
     * @return 查询所有的区块多媒体信息
     */
    public List<AreaMedia> findAll() {
        return areaMediaRepository.findAll();
    }

    /**
     * @return 查询所有的区块多媒体信息，有数据权限限制
     */
    public List<AreaMedia> findByAuthKey(HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return areaMediaRepository.findByAuthKeyStartsWith(authKey);
    }

    /**
     * @param id
     * @return 根据id查询区块多媒体信息
     */
    public AreaMedia findById(Long id) {
        return areaMediaRepository.findOne(id);
    }

    /**
     * @param fileName 多媒体文件名
     * @return 根据多媒体文件名查询
     */
    public AreaMedia findByFileName(String fileName) {
        return areaMediaRepository.findByFileName(fileName);
    }

    /**
     * @param idsList
     * @return 根据ids列表批量删除文件
     */
    public List<ReturnObject> deleteInBatch(List<Long> idsList) {
        List<ReturnObject> resList = new ArrayList<ReturnObject>();
        if (!idsList.isEmpty()) {
            for (int i = 0; i < idsList.size(); i++) {
                ReturnObject delRO = delete(idsList.get(i));
                resList.add(delRO);
            }
            System.out.println("======批量删除区块多媒体文件========idsList======" + idsList + "===resList===" + resList);
            return resList;
        } else {
            return null;
        }
    }

    /**
     * @param id
     * @return 根据ID删除区块多媒体文件以及数据库记录
     */
    public ReturnObject delete(Long id) {
        //从数据库中查询记录
        AreaMedia areaMedia = areaMediaRepository.findOne(id);
        if (areaMedia == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的区块多媒体信息不存在！");
        } else {
            //从数据库中删除该记录
            try {
                areaMediaRepository.delete(areaMedia);
                AreaMedia areaMedia1 = areaMediaRepository.findOne(id);
                return commonDataService.getReturnType(areaMedia1 == null, "区块多媒体信息删除成功！", "区块多媒体信息删除失败！");
            } catch (Exception e) {
                return commonDataService.getReturnType(false, "区块多媒体信息删除成功！", "区块多媒体信息删除失败！");
            }
        }
    }


    /**
     * @param file
     * @param treeNodeId
     * @param request
     * @param session
     * @param userName
     * @return 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     */
    @Transactional
    public AreaMedia uploadFileAndSavePath(MultipartFile file, Long treeNodeId, Long areaId, HttpServletRequest request, HttpSession session, String userName) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//精确到时分秒
        //获取当前时间，并且转为字符串
        Date current = new Date();
        String timeStr = sdf.format(current);
        String dirStr = "mediaDocs/areaMedia/" + timeStr;//区块多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        if (!UploadUtil.createDirectory(realDir)) {//目录创建失败则返回null，目录存在或者创建成功就继续执行
            return null;
        }
        String fileName = file.getOriginalFilename().replace(" ", "");//获取文件名，去掉文件名中的空格
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
        boolean resultFTP = FTPUploader.uploadFileToFtp(filePath, FTPconfig.IP, FTPconfig.PORT, FTPconfig.USERNAME, FTPconfig.PASSWORD, FTPconfig.ROOT, fileName, dirStr);//将Tomcat里的文件上传到FTP

        //仅对图片进行压缩和显示缩略图
        if (mediaTypeId == 2L && !fileName.endsWith(".tif")) {
            String thumbNailUrl = StringUtils.renameNgnixFile(filePath);
            try {
                ThumbNailUtil.saveImageAsJpg(filePath, thumbNailUrl, 200, 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String thumbFileName = StringUtils.renameFile(fileName);
            FTPUploader.uploadFileToFtp(thumbNailUrl, FTPconfig.IP, FTPconfig.PORT, FTPconfig.USERNAME, FTPconfig.PASSWORD, FTPconfig.ROOT, thumbFileName, dirStr);
        }

        if (resultFTP) {
            //ftp上传成功后删除 tomcat中的目录
            File existFile = new File(filePath);
            if (existFile.exists()) {
                existFile.delete();
            }
            return saveUploadLog(fileName, file, dirStr, mediaTypeId, treeNodeId, session, userName, current, areaId);
        } else {
            log.info("--------AreaMediaFile----upload failed------" + filePath);
            return null;
        }
    }


    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    public List<AreaMedia> findByMediaTypesId(Long mediaTypeId) {
        return areaMediaRepository.findByMediaTypesId(mediaTypeId);
    }

    /**
     * @param areaId
     * @param mediaTypeId
     * @return 根据区块id以及多媒体类型id查询该属于该区块的该多媒体类型的信息列表
     */
    public List<AreaMedia> findByAreaIdAndMediaTypesIdAndAuthKey(Long areaId, Long mediaTypeId, HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return areaMediaRepository.findByAreaIdAndMediaTypesIdAndAuthKeyStartsWith(areaId, mediaTypeId, authKey);
    }

    /**
     * @param id
     * @return 根据id进行区块多媒体文件的授权
     */
    public ReturnObject authorizeById(Long id) {
        //从数据库中查询记录
        AreaMedia areaMedia = areaMediaRepository.findOne(id);
        if (areaMedia == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的区块多媒体信息不存在！");
        } else {
            try {
                areaMedia.setStatus("1");//将区块多媒体文件记录的状态设置为1，则表示成功授权
                areaMedia = areaMediaRepository.save(areaMedia);//修改status属性后要保存到数据库中
                System.out.println("======单个授权区块多媒体文件====areaMedia===" + areaMedia);
                return commonDataService.getReturnType(areaMedia.getStatus().equals("1"), "授权成功！", "授权失败！");
            } catch (Exception e) {
                return commonDataService.getReturnType(false, "", "id为" + id + "的区块多媒体信息授权异常！");
            }
        }
    }

    /**
     * @param idsList ids列表
     * @return 根据ids列表对区块多媒体文件进行批量授权
     */
    public List<ReturnObject> authorizeInBatch(List<Long> idsList) {
        List<ReturnObject> resList = new ArrayList<ReturnObject>();
        if (!idsList.isEmpty()) {
            for (int i = 0; i < idsList.size(); i++) {
                ReturnObject authorizeRO = authorizeById(idsList.get(i));
                resList.add(authorizeRO);
            }
            System.out.println("======批量授权区块多媒体文件========idsList======" + idsList + "===resList===" + resList);
            return resList;
        } else {
            return null;
        }
    }


    /**
     * @param areaId      区块id
     * @param mediaTypeId 媒体类型id
     * @return
     */
    public List<AreaMedia> findPicsByAreaIdAndMediaTypeId(Long areaId, Long mediaTypeId) {
        return areaMediaRepository.findByArea_IdAndMediaTypes_Id(areaId, mediaTypeId);
    }


    /**
     * @param fileName
     * @param file
     * @param dirStr
     * @param mediaTypeId
     * @param treeNodeId
     * @param session
     * @param userName
     * @param current
     * @param areaId
     * @return 保存上传日志
     */
    public AreaMedia saveUploadLog(String fileName, MultipartFile file, String dirStr, Long mediaTypeId, Long treeNodeId, HttpSession session, String userName, Date current, Long areaId) {
        //如果上传到ftp成功，就保存多媒体信息到数据库
        AreaMedia areaMedia = new AreaMedia();
        areaMedia.setFileName(fileName);
        areaMedia.setFileSize(file.getSize());
        String nginxUrl = ftPconfig.getFTPUrl() + "/" + dirStr + "/" + fileName;
        areaMedia.setFileRelativeUrl(nginxUrl);
        areaMedia.setMediaTypes(mediaTypeRepository.findOne(mediaTypeId));
        areaMedia.setMediaTree(mediaTreeRepository.findOne(treeNodeId));
        areaMedia.setThumbNailUrl((mediaTypeId == 2L) ? StringUtils.renameNgnixFile(nginxUrl) : nginxUrl);
        areaMedia.setStatus("0");//默认为未审核状态，设置为1则为已审核状态
        areaMedia.setAuthKey(DataFilterUtils.getAuthKey(session));
        areaMedia.setUser(userRepository.findByUserName(userName));//设置上传的用户
        areaMedia.setUploadDate(current);//设置上传时间
        areaMedia.setArea(areaRepository.findOne(areaId));
        return areaMediaRepository.save(areaMedia);

    }

}
