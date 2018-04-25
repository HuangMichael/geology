package com.yhb.service.media;

import com.yhb.dao.location.LocationRepository;
import com.yhb.dao.mediaTree.MediaTreeRepository;
import com.yhb.dao.mediaType.MediaTypeRepository;
import com.yhb.dao.remoteSensorMedia.RemoteSensorMediaRepository;
import com.yhb.dao.user.UserRepository;
import com.yhb.domain.remoteSensorMedia.RemoteSensorMedia;
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
public class RemoteSensorMediaService extends BaseService {
    @Autowired
    RemoteSensorMediaRepository remoteSensorMediaRepository;

    @Autowired
    MediaTypeRepository mediaTypeRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    MediaTreeRepository mediaTreeRepository;
    @Autowired
    FTPconfig ftPconfig;

    /**
     * @param remoteSensorMedia
     * @return 保存的遥感多媒体信息
     */
    public RemoteSensorMedia save(RemoteSensorMedia remoteSensorMedia) {
        return remoteSensorMediaRepository.save(remoteSensorMedia);
    }

    /**
     * @param remoteSensorMediaList
     * @return 保存的遥感多媒体信息列表
     */
    public List<RemoteSensorMedia> save(List<RemoteSensorMedia> remoteSensorMediaList) {
        return remoteSensorMediaRepository.save(remoteSensorMediaList);
    }

    /**
     * @return 查询所有的遥感多媒体信息
     */
    public List<RemoteSensorMedia> findByAuthKey(HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return remoteSensorMediaRepository.findByAuthKeyStartsWith(authKey);
    }

    /**
     * @param id
     * @return 根据id查询遥感信息
     */
    public RemoteSensorMedia findById(Long id) {
        return remoteSensorMediaRepository.findOne(id);
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
            System.out.println("======批量删除遥感多媒体文件========idsList======" + idsList + "===resList===" + resList);
            return resList;
        } else {
            return null;
        }
    }

    /**
     * @param id
     * @return 根据ID删除遥感多媒体文件以及数据库记录
     */
    public ReturnObject delete(Long id) {
        //从数据库中查询记录
        RemoteSensorMedia remoteSensorMedia = remoteSensorMediaRepository.findOne(id);
        if (remoteSensorMedia == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的遥感多媒体信息不存在！");
        } else {

            try {
                remoteSensorMediaRepository.delete(remoteSensorMedia);
                RemoteSensorMedia remoteSensorMedia1 = remoteSensorMediaRepository.findOne(id);
                return commonDataService.getReturnType(remoteSensorMedia1 == null, "遥感多媒体信息删除成功！", "遥感多媒体信息删除失败！");
            } catch (Exception e) {
                return commonDataService.getReturnType(false, "遥感多媒体信息删除成功！", "遥感多媒体信息删除失败！");
            }
        }
    }

    /**
     * @param file
     * @param request
     * @return 上传文件到文件服务器
     */
    @Transactional
    public ReturnObject upload(MultipartFile file, HttpServletRequest request) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = sdf.format(new Date());
        String dirStr = "mediaDocs\\remoteSensorMedia\\" + timeStr;//遥感多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        String fileName = file.getOriginalFilename();//文件名
        String filePath = realDir + "\\" + fileName;//绝对文件路径
        Boolean result = UploadUtil.uploadFile(file, filePath);
        return commonDataService.getReturnType(result, "文件上传成功！", "文件上传失败！");
    }

    /**
     * @param file
     * @param locationId
     * @param request
     * @return 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     */
    @Transactional
    public RemoteSensorMedia uploadAndSave(MultipartFile file, Long locationId, HttpServletRequest request, HttpSession session, String userName) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date current = new Date();//获取当前时间
        String timeStr = sdf.format(current);
        String dirStr = "mediaDocs/remoteSensorMedia/" + timeStr;//遥感多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        System.out.println("--------RemoteSensorMediaFile---realDir--------" + realDir);
        if (!UploadUtil.createDirectory(realDir)) {//目录创建失败则返回null
            return null;
        }
        String fileName = file.getOriginalFilename();//文件名
        String filePath = realDir + "\\" + fileName;//绝对文件路径
        String type = file.getContentType();//获取文件类型
        Long mediaTypeId = 1L;//多媒体类型默认为文档
        if (type.contains("image")) {
            mediaTypeId = 2L;//多媒体类型 图片
        } else if (type.contains("video")) {
            mediaTypeId = 3L;//多媒体类型 视频
        }

        System.out.println("---RemoteSensorMediaFile---filePath---" + filePath + "----filesize----" + file.getSize()
                + "----type----" + type);
        Boolean result = UploadUtil.uploadFile(file, filePath);//上传文件到ftp
        if (result) {
            //如果上传到ftp成功，就保存多媒体信息到数据库
            RemoteSensorMedia remoteSensorMedia = new RemoteSensorMedia();
            remoteSensorMedia.setFileName(fileName);
            remoteSensorMedia.setFileSize(file.getSize());
            remoteSensorMedia.setFileRelativeUrl(dirStr + "\\" + fileName);//设置文件的相对路径，"docs\\remoteSensorMedia"+文件名
            remoteSensorMedia.setFileAbsoluteUrl(filePath);//设置文件的绝对路径，精确到盘符，如："E:\bmis0628\....\webapp"+"docs\\remoteSensorMedia"+文件名
            remoteSensorMedia.setMediaTypes(mediaTypeRepository.findOne(mediaTypeId));
            remoteSensorMedia.setLocation(locationRepository.findOne(locationId));
            remoteSensorMedia.setStatus("0");//默认为未审核状态，设置为1则为已审核状态
            remoteSensorMedia.setAuthKey(DataFilterUtils.getAuthKey(session));
            remoteSensorMedia.setUser(userRepository.findByUserName(userName));//设置上传的用户
            remoteSensorMedia.setUploadDate(current);//设置上传时间
            RemoteSensorMedia remoteSensorMedia1 = remoteSensorMediaRepository.save(remoteSensorMedia);
            if (remoteSensorMedia1 != null) {
                System.out.println("--------RemoteSensorMediaFile----上传成功 保存成功----" + filePath);
                return remoteSensorMedia1;
            } else {
                System.out.println("--------RemoteSensorMediaFile----上传成功 保存失败----" + filePath);
                return null;
            }
        } else {
            System.out.println("--------RemoteSensorMediaFile----上传失败----" + filePath);
            return null;
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
    public RemoteSensorMedia uploadFileAndSavePath(MultipartFile file, Long treeNodeId, HttpServletRequest request, HttpSession session, String userName) {
        String contextPath = SessionUtil.getContextPath(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//精确到时分秒
        //获取当前时间，并且转为字符串
        Date current = new Date();
        String timeStr = sdf.format(current);
        String dirStr = "mediaDocs/remoteSensorMedia/" + timeStr;//项目多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
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
            return saveUploadLog(fileName, file, dirStr, mediaTypeId, treeNodeId, session, userName, current);
        } else {
            log.info("--------RemoteSensorMediaFile----upload failed------" + filePath);
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
     * @return 保存上传日志
     */
    public RemoteSensorMedia saveUploadLog(String fileName, MultipartFile file, String dirStr, Long mediaTypeId, Long treeNodeId, HttpSession session, String userName, Date current) {
        //如果上传到ftp成功，就保存多媒体信息到数据库
        RemoteSensorMedia remoteSensorMedia = new RemoteSensorMedia();
        remoteSensorMedia.setFileName(fileName);
        remoteSensorMedia.setFileSize(file.getSize());
        String nginxUrl = ftPconfig.getFTPUrl() + "/" + dirStr + "/" + fileName;
        remoteSensorMedia.setFileRelativeUrl(nginxUrl);//设置文件的相对路径，"docs\\AreaMedia"+文件名
        remoteSensorMedia.setThumbNailUrl((mediaTypeId == 2L) ? StringUtils.renameNgnixFile(nginxUrl) : nginxUrl);
        remoteSensorMedia.setMediaTypes(mediaTypeRepository.findOne(mediaTypeId));
        remoteSensorMedia.setMediaTree(mediaTreeRepository.findOne(treeNodeId));
        remoteSensorMedia.setStatus("0");//默认为未审核状态，设置为1则为已审核状态
        remoteSensorMedia.setAuthKey(DataFilterUtils.getAuthKey(session));
        remoteSensorMedia.setUser(userRepository.findByUserName(userName));//设置上传的用户
        remoteSensorMedia.setUploadDate(current);//设置上传时间
        return remoteSensorMediaRepository.save(remoteSensorMedia);
    }

    /**
     * @param locationId
     * @return 根据位置id查询该位置的所有的多媒体信息列表
     */
    public List<RemoteSensorMedia> findByLocationIdAndAuthKey(Long locationId, HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return remoteSensorMediaRepository.findByLocationIdAndAuthKeyStartsWith(locationId, authKey);
    }

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    public List<RemoteSensorMedia> findByMediaTypesId(Long mediaTypeId) {
        return remoteSensorMediaRepository.findByMediaTypesId(mediaTypeId);
    }

    /**
     * @param locationId
     * @param mediaTypeId
     * @return 根据位置id以及多媒体类型id查询该属于该位置的该多媒体类型的信息列表
     */
    public List<RemoteSensorMedia> findByLocationIdAndMediaTypesIdAndAuthKey(Long locationId, Long mediaTypeId, HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return remoteSensorMediaRepository.findByLocationIdAndMediaTypesIdAndAuthKeyStartsWith(locationId, mediaTypeId, authKey);
    }

    /**
     * @param id
     * @return 根据id进行遥感多媒体文件授权
     */
    public ReturnObject authorizeById(Long id) {
        //从数据库中查询记录
        RemoteSensorMedia remoteSensorMedia = remoteSensorMediaRepository.findOne(id);
        if (remoteSensorMedia == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的遥感多媒体信息不存在！");
        } else {
            try {
                remoteSensorMedia.setStatus("1");//将遥感多媒体文件记录的状态设置为1，则表示成功授权
                remoteSensorMedia = remoteSensorMediaRepository.save(remoteSensorMedia);//修改status属性后要保存到数据库中
                System.out.println("======单个授权遥感多媒体文件====remoteSensorMedia===" + remoteSensorMedia);
                return commonDataService.getReturnType(remoteSensorMedia.getStatus().equals("1"), "授权成功！", "授权失败！");
            } catch (Exception e) {
                return commonDataService.getReturnType(false, "", "id为" + id + "的遥感多媒体信息授权异常！");
            }
        }
    }

    /**
     * @param idsList
     * @return 根据ids列表对多媒体文件进行批量授权
     */
    public List<ReturnObject> authorizeInBatch(List<Long> idsList) {
        List<ReturnObject> resList = new ArrayList<ReturnObject>();
        if (!idsList.isEmpty()) {
            for (int i = 0; i < idsList.size(); i++) {
                ReturnObject authorizeRO = authorizeById(idsList.get(i));
                resList.add(authorizeRO);
            }
            System.out.println("======批量授权遥感多媒体文件========idsList======" + idsList + "===resList===" + resList);
            return resList;
        } else {
            return null;
        }
    }
}
