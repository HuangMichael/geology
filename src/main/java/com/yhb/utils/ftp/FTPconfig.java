package com.yhb.utils.ftp;

import com.yhb.domain.sysInfo.SysParams;
import com.yhb.service.sysInfo.SysParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangbin on 2017/10/31.
 */
@Component
public class FTPconfig {
    @Autowired
    SysParamsService sysParamsService;

    public static final String IP = "10.0.0.14";
    public static final int PORT = 21;
    public static final int NGINX_PORT = 9090;
    public static final String USERNAME = "huiteng";
    public static final String PASSWORD = "huiteng2018";
    public static final String ROOT = "f:/ftp/mediaDocs";

//    public static final String IP = "58.213.65.98";//项目在服务器上运行时，设置为服务器的本地IP  192.168.1.2
//    public static final int PORT = 21;
//    public static final int NGINX_PORT = 9090;
//    public static final String USERNAME = "bmis";
//    public static final String PASSWORD = "bmislinkbit";
//    public static final String ROOT = "e:/ftp/mediaDocs";

    /**
     * @return 获取FTP URL地址
     */
    public String getFTPUrl() {
        SysParams sysParams = sysParamsService.findByParamName("FTP_URL");
        return sysParams.getParamValue();
    }
}
