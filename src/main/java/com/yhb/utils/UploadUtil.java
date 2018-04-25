package com.yhb.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by huangbin on 2017/6/16 0022.
 * 上传附件工具类
 */
@Slf4j
public class UploadUtil {
    /**
     * @param file     二进制文件
     * @param filePath 目标文件路径
     */
    public static boolean uploadFile(MultipartFile file, String filePath) {
        boolean res = false;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
                BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream);
                stream.write(bytes);
                stream.close();
                res = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

//C:\Program Files\apache-tomcat-9.0.0.M26\webapps\ROOT\mediaDocs

    /**
     * @param theURL
     * @param filePath
     * @param fileName
     * @throws IOException
     */
    public static void downloadFile(URL theURL, String filePath, String fileName) throws IOException {
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            //文件路径不存在时，自动创建目录
            dirFile.mkdir();
        }
        //从服务器上获取图片并保存
        URLConnection connection = theURL.openConnection();
        InputStream in = connection.getInputStream();
        FileOutputStream os = new FileOutputStream(filePath + fileName);
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) > 0) {
            os.write(buffer, 0, read);
        }
        os.close();
        in.close();
    }

    /**
     * @param dirString
     * @return 判断是否存在dirString的目录，目录存在 以及 不存在而手动创建成功后 均返回true，创建失败返回false
     */
    public static boolean createDirectory(String dirString) {
        boolean res = false;//返回结果，默认false
        dirString = dirString.replace(" ", "");
        File dir = new File(dirString);
        if (dir.exists()) {
            System.out.println(dirString + "目标目录已经存在");
            res = true;
        } else {
            //如果创建目录失败，返回false
            if (!dir.mkdirs()) {
                System.out.println("创建目录失败，" + dirString + "返回false");
                res = false;
            } else {
                System.out.println("创建目录成功，" + dirString + "返回true！");
                res = true;
            }
        }
        return res;
    }

}

