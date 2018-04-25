package com.yhb.service.base;

import com.yhb.utils.DateUtils;
import com.yhb.utils.SessionUtil;
import com.yhb.utils.UploadUtil;
import com.yhb.utils.ftp.FTPUploader;
import com.yhb.utils.ftp.FTPconfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;

/**
 * Created by huangbin on 2017/10/30.
 */
@Service
@Slf4j
public class UploadService {

    private String ip = "192.168.0.145";
    private int port = 21;
    private String userName = "bmis";
    private String password = "bmislinkbit";
    private String ftpUrl = "f:/ftp";



    /**
     * @param file    上传的文件
     * @param request 请求
     * @param appType 应用类型（area|project|remoteSensor）
     * @return
     */
    public boolean uploadFileToWebServer(MultipartFile file, HttpServletRequest request, String appType) {
        String contextPath = SessionUtil.getContextPath(request);
        String dirStr = genDocUrlByAppType(appType);//区块多媒体存放的文件夹，加上时间戳来唯一标识该时间上传的所有的文件
        String realDir = contextPath + dirStr;//绝对目录路径
        if (!UploadUtil.createDirectory(realDir)) {//目录创建失败则返回null
            return false;
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
        return UploadUtil.uploadFile(file, filePath);//上传文件到ftp
    }


    /**
     * @param appType 根据应用名称 生成时间戳文件夹
     * @return
     */
    public String genDocUrlByAppType(String appType) {
        return "mediaDocs/" + appType + "Media/" + DateUtils.convertDate2Str(new Date(), appType);
    }


    /**
     * @param fileName
     * @return
     */
    public String getNginxUrl(String fileName) {

        return "http://" + FTPconfig.IP + ":" + FTPconfig.PORT + "/" + fileName;
    }


    public String getContextPath(HttpServletRequest request) {

        return "";
    }
}
