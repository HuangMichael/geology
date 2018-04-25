package com.yhb.aop;


import com.yhb.asyn.AsyncAccessLog;
import com.yhb.domain.log.AppAccessLog;
import com.yhb.domain.menu.Menu;
import com.yhb.domain.sysInfo.SysParams;
import com.yhb.domain.user.User;
import com.yhb.service.menu.MenuService;
import com.yhb.service.sysInfo.SysParamsService;
import com.yhb.utils.SessionUtil;
import com.yhb.utils.search.AnnotationUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.Future;

/**
 * 页面访问日志aop
 */
@Component
@Aspect
@Slf4j
public class AppAccessAop {
    @Autowired
    AsyncAccessLog asyncAccessLog;
    @Autowired
    AnnotationUtil annotationUtil;

    @Autowired
    SysParamsService sysParamsService;
    @Autowired
    MenuService menuService;

    @After("execution(* com.yhb.controller.*.*Controller.index(..))||execution(* com.yhb.controller.*.*Controller.index3d(..))||execution(* com.yhb.controller.*.*Controller.list(..))")
    public void writeAccessLog(JoinPoint point) throws InterruptedException {

        //设置日志开关

        //从系统参数表中查询是否需要记录访问日志
        SysParams sysParams = sysParamsService.findByParamName("ACCESS_LOG_STATUS");
        String logaable = sysParams.getParamValue();
        if (!logaable.equals("1")) {

            log.info("是否记录应用访问日志？" + logaable);
            return;
        }
        String url = annotationUtil.getMethodMappingValueByJoinPoint(point);
        Menu menu = menuService.findByEventAndMenuTypeAndStatus(url, "APP", "1");
        String appName = menu.getMenuDesc() != null ? menu.getMenuDesc() : "";

        Object objects[] = point.getArgs();
        HttpServletRequest request = null;
        User user = null;
        if (objects != null && objects.length > 0) {
            request = (HttpServletRequest) objects[0];
            user = SessionUtil.getCurrentUser(request);
        }
        //获取到查询的参数 进行分装  记录日志
        AppAccessLog appAccessLog = new AppAccessLog();
        appAccessLog.setAppName(appName);
        appAccessLog.setUrl(url);
        String ip = request.getRemoteAddr() != null ? request.getRemoteAddr() : "未知";
        appAccessLog.setIp(request.getRemoteAddr());
        appAccessLog.setAccessTime(new Date());


        String userName = (user != null) ? user.getUserName() + "[" + user.getPersonName() + "]" : "用户";
        log.info("userName from " + this.getClass().getName() + userName);
        appAccessLog.setUserName(userName);
        long begin = System.currentTimeMillis();
        Future<String> test1 = asyncAccessLog.writeAccessLog(appAccessLog);
        while (true) {
            if (test1.isDone())
                break;
        }
        log.info("end 耗时: " + (System.currentTimeMillis() - begin));
    }
}
