package com.yhb.utils.ftp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

import java.io.*;

/**
 * Created by huangbin on 2017/10/30.
 */

@Slf4j
public class FTPUploader {

    public static FTPClient ftp;

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param ip
     * @param port     FTP服务器端口
     * @param userName FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录
     * @param fileName 上传到FTP服务器上的文件名
     * @param input    输入流
     * @param fileUrl  要创建的新文件夹
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String ip, int port, String userName, String password, String path, String fileName, InputStream input, String fileUrl) {
        boolean success = false;
        try {
            int reply;
            ftp = new FTPClient();
            ftp.connect(ip, port);//连接FTP服务器
            boolean connected = ftp.isConnected();
            if (!connected) {
                return connected;
            }
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            boolean loginStatus = ftp.login(userName, password);//登录
            ftp.setDataTimeout(1000 * 60 * 30);   //设置传输超时（30分钟）
            ftp.setConnectTimeout(60000);       //连接超时为60秒
            String multiDirectory = fileUrl;
            log.info("multiDirectory --------------" + fileUrl);
            boolean result = createMultiDirectory(ftp, multiDirectory);
            log.info("result of createMultiDirectory --------------" + result);
            //指定上传路径
            ftp.changeWorkingDirectory(path);
            log.info("changeWorkingDirectory --------------" + path);
            //指定上传文件的类型 二进制文件
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            fileName = new String(fileName.getBytes("GBK"), "iso-8859-1");// 转换后的目录名或文件名。
            ftp.storeFile(fileName, input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("ftp IOException--------------");
        } finally {
            if (ftp.isConnected()) {
                try {
                    log.info("ftp.isConnected()--------------" + ftp.isConnected());
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        log.info("ftp.success()--------------" + success);
        return success;
    }


    /**
     * @param fileUrl  文件相对路径
     * @param ip       ftp ip地址
     * @param port     ftp端口
     * @param userName 用户名
     * @param password 密码
     * @param ftpUrl   ftp根路径
     * @param fileName 保存的文件名
     * @param dirStr   保存在ftp的文件路径
     * @return
     */
    public static boolean uploadFileToFtp(String fileUrl, String ip, int port, String userName, String password, String ftpUrl, String fileName, String dirStr) {
        boolean flag = false;
        //如果文件夹不存在 创建文件夹
        File file = new File(fileUrl);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            flag = uploadFile(ip, port, userName, password, ftpUrl, fileName, fileInputStream, dirStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * @param ftpClient
     * @param multiDirectory
     * @return 远程FTP服务器创建多级目录，创建目录失败或发生异常则返回false
     */
    private static boolean createMultiDirectory(FTPClient ftpClient, String multiDirectory) {
        boolean bool = false;
        try {
            String[] dirs = multiDirectory.split("/");
            ftpClient.changeWorkingDirectory("/");
            ftp.setControlEncoding("UTF-8");//注意编码格式
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            conf.setServerLanguageCode("zh");//中文
            //按顺序检查目录是否存在，不存在则创建目录
            for (int i = 1; dirs != null && i < dirs.length; i++) {
                if (!ftpClient.changeWorkingDirectory(dirs[i])) {
                    if (ftpClient.makeDirectory(dirs[i])) {
                        if (!ftpClient.changeWorkingDirectory(dirs[i])) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }

            bool = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return bool;
        }
    }

}
