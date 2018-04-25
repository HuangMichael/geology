package com.yhb.service.media;

import com.yhb.archives.Archives;
import com.yhb.archives.ArchivesRepository;
import com.yhb.dao.areaProject.AreaProjectRepository;
import com.yhb.dao.areaProject.ProjectMediaRepository;
import com.yhb.dao.mediaTree.MediaTreeRepository;
import com.yhb.dao.mediaType.MediaTypeRepository;
import com.yhb.dao.user.UserRepository;

import com.yhb.domain.area.AreaMedia;
import com.yhb.domain.areaProject.ProjectMedia;
import com.yhb.domain.mediaCatalog.MediaTree;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.*;
import com.yhb.utils.ftp.FTPUploader;
import com.yhb.utils.ftp.FTPconfig;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Service
@Slf4j
public class ProjectMediaService extends BaseService {
    @Autowired
    ProjectMediaRepository projectMediaRepository;
    @Autowired
    MediaTypeRepository mediaTypeRepository;
    @Autowired
    AreaProjectRepository areaProjectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommonDataService commonDataService;
    @Autowired
    MediaTreeRepository mediaTreeRepository;
    @Autowired
    FTPconfig ftPconfig;
    @Autowired
    ArchivesRepository archivesRepository;

    /**
     * @param projectMedia
     * @return 保存的项目多媒体信息
     */
    public ProjectMedia save(ProjectMedia projectMedia) {
        return projectMediaRepository.save(projectMedia);
    }

    /**
     * @param projectMediaList
     * @return 保存的项目多媒体信息列表
     */
    public List<ProjectMedia> save(List<ProjectMedia> projectMediaList) {
        return projectMediaRepository.save(projectMediaList);
    }

    /**
     * @param id
     * @return 根据id查询项目多媒体信息
     */
    public ProjectMedia findById(Long id) {
        return projectMediaRepository.findOne(id);
    }

    /**
     * @param id
     * @return 根据ID删除项目多媒体文件以及数据库记录
     */
    public ReturnObject delete(Long id) {
        //从数据库中查询记录
        ProjectMedia projectMedia = projectMediaRepository.findOne(id);
        if (projectMedia == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的项目多媒体信息不存在！");
        } else {
//            //从项目多媒体文件夹下真正的删除文档
//            String fileAbsoluteUrl = projectMedia.getFileAbsoluteUrl();
//            File file = new File(fileAbsoluteUrl);
//            //如果文件路径所对应的文件存在，并且是一个文件，则直接删除
//            if (file.exists() && file.isFile()) {
//                if (file.delete()) {
//                    //如果文件删除成功，就从数据库中也删除该记录
            try {
                projectMediaRepository.delete(projectMedia);
                ProjectMedia projectMedia1 = projectMediaRepository.findOne(id);
                return commonDataService.getReturnType(projectMedia1 == null, "项目多媒体信息删除成功！", "项目多媒体信息删除失败！");
            } catch (Exception e) {
                return commonDataService.getReturnType(true, "项目多媒体信息删除成功！", "项目多媒体信息删除失败！");
            }
        }
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
            System.out.println("======批量删除项目多媒体文件========idsList======" + idsList + "===resList===" + resList);
            return resList;
        } else {
            return null;
        }
    }

    /**
     * @param file
     * @param request
     * @return
     */
    @Transactional
    public ReturnObject upload(MultipartFile file, HttpServletRequest request) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String dirStr = "mediaDocs\\projectMedia\\" + timeStr;//项目多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        dirStr = dirStr.replace(" ", "");
        String realDir = contextPath + dirStr;//绝对目录路径
        String fileName = file.getOriginalFilename();//文件名
        String filePath = realDir + "\\" + fileName;//绝对文件路径
        filePath = filePath.replace(" ", "");
        Boolean result = UploadUtil.uploadFile(file, filePath);
        return commonDataService.getReturnType(result, "文件上传成功！", "文件上传失败！");
    }

    /**
     * @param file
     * @param treeNodeId
     * @param projectId
     * @param request
     * @param session
     * @param userName
     * @return 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     */
    @Transactional
    public Archives uploadFileAndSavePath(MultipartFile file, Long treeNodeId, Long projectId, HttpServletRequest request, HttpSession session, String userName) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//精确到时分秒
        //获取当前时间，并且转为字符串
        Date current = new Date();
        String timeStr = sdf.format(current);
        String dirStr = "mediaDocs/projectMedia/" + timeStr;//项目多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
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
        boolean resultFTP = FTPUploader.uploadFileToFtp(filePath, FTPconfig.IP, FTPconfig.PORT, FTPconfig.USERNAME, FTPconfig.PASSWORD, FTPconfig.ROOT, fileName, dirStr);

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
            //ftp上传成功后删除Tomcat中的目录
            File existFile = new File(filePath);
            if (existFile.exists()) {
                existFile.delete();
            }

            saveUploadLog(fileName, file, dirStr, mediaTypeId, treeNodeId, session, userName, current, projectId);
            return saveUploadLog2(fileName, file, dirStr, mediaTypeId, treeNodeId, session, userName, current, projectId);
        } else {
            log.info("--------ProjectMediaFile----upload failed------" + filePath);
            return null;
        }
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
     * @param projectId
     * @return 保存上传日志
     */
    public ProjectMedia saveUploadLog(String fileName, MultipartFile file, String dirStr, Long mediaTypeId, Long treeNodeId, HttpSession session, String userName, Date current, Long projectId) {
        //如果上传到ftp成功，就保存多媒体信息到数据库
        ProjectMedia projectMedia = new ProjectMedia();
        projectMedia.setFileName(fileName);
        projectMedia.setFileSize(file.getSize());
        String nginxUrl = ftPconfig.getFTPUrl() + "/" + dirStr + "/" + fileName;
        projectMedia.setFileRelativeUrl(nginxUrl);//设置为NGINX的路径
        projectMedia.setThumbNailUrl((mediaTypeId == 2L) ? StringUtils.renameNgnixFile(nginxUrl) : nginxUrl);
        projectMedia.setMediaTypes(mediaTypeRepository.findOne(mediaTypeId));
        projectMedia.setAreaProject(areaProjectRepository.findOne(projectId));
        projectMedia.setMediaTree(mediaTreeRepository.findOne(treeNodeId));
        projectMedia.setStatus("0");//默认为未审核状态，设置为1则为已审核状态
        projectMedia.setAuthKey(DataFilterUtils.getAuthKey(session));
        projectMedia.setUser(userRepository.findByUserName(userName));//设置上传的用户
        projectMedia.setUploadDate(current);//设置上传时间
        return projectMediaRepository.save(projectMedia);
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
     * @param projectId
     * @return 保存上传日志
     */
    public Archives saveUploadLog2(String fileName, MultipartFile file, String dirStr, Long mediaTypeId, Long treeNodeId, HttpSession session, String userName, Date current, Long projectId) {
        //如果上传到ftp成功，就保存多媒体信息到数据库
        Archives archives = new Archives();
        archives.setArchiveNo(DateUtils.convertDate2Str(current, "yyyyMMddhhmmss"));
        archives.setMediaTree(mediaTreeRepository.findOne(treeNodeId));
        archives.setShortName(fileName);
        String path = ftPconfig.getFTPUrl() + "/" + dirStr + "/" + fileName;
        archives.setName(path);
        archives.setCreatedBy(userName);
        archives.setSecClass("1");
        archives.setCreateDate(current);//设置上传时间
        archives.setAuthKey(DataFilterUtils.getAuthKey(session));
        archives.setStatus("0");
        return archivesRepository.save(archives);
    }

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    public List<ProjectMedia> findByMediaTypesId(Long mediaTypeId) {
        return projectMediaRepository.findByMediaTypesId(mediaTypeId);
    }

    /**
     * @param areaProjectId
     * @param mediaTypeId
     * @return 根据项目id以及多媒体类型id查询该属于该项目的该多媒体类型的信息列表
     */
    public List<ProjectMedia> findByAreaProjectIdAndMediaTypesIdAndAuthKey(Long areaProjectId, Long mediaTypeId, HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return projectMediaRepository.findByAreaProjectIdAndMediaTypesIdAndAuthKeyStartsWith(areaProjectId, mediaTypeId, authKey);
    }

    /**
     * @param id
     * @return 根据id进行多媒体文件授权
     */
    public ReturnObject authorizeById(Long id) {
        //从数据库中查询记录
        ProjectMedia projectMedia = projectMediaRepository.findOne(id);
        if (projectMedia == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的项目多媒体信息不存在！");
        } else {
            try {
                projectMedia.setStatus("1");//将项目多媒体文件记录的状态设置为1，则表示成功授权
                projectMedia = projectMediaRepository.save(projectMedia);//修改status属性后要保存到数据库中
                return commonDataService.getReturnType(projectMedia.getStatus().equals("1"), "授权成功！", "授权失败！");
            } catch (Exception e) {
                return commonDataService.getReturnType(false, "", "id为" + id + "的项目多媒体信息授权异常！");
            }
        }
    }

    /**
     * @param idsList
     * @return 根据ids列表对项目多媒体文件进行批量授权
     */
    public List<ReturnObject> authorizeInBatch(List<Long> idsList) {
        List<ReturnObject> resList = new ArrayList<ReturnObject>();
        if (!idsList.isEmpty()) {
            for (int i = 0; i < idsList.size(); i++) {
                ReturnObject authorizeRO = authorizeById(idsList.get(i));
                resList.add(authorizeRO);
            }
            System.out.println("======批量授权项目多媒体文件========idsList======" + idsList + "===resList===" + resList);
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
    public List<ProjectMedia> findPicsByProjectIdAndMediaTypeId(Long areaId, Long mediaTypeId) {
        return projectMediaRepository.findByAreaProject_IdAndMediaTypes_Id(areaId, mediaTypeId);
    }


}
