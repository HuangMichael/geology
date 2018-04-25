package com.yhb.aop;


import com.yhb.asyn.AsyncQueryLog;
import com.yhb.domain.log.AppQueryLog;
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
 * 用户查询日志aop
 */
@Component
@Aspect
@Slf4j
public class UserSearchAop {

    @Autowired
    AsyncQueryLog asyncQueryLog;
    @Autowired
    MenuService menuService;
    @Autowired
    AnnotationUtil annotationUtil;

    @Autowired
    SysParamsService sysParamsService;

    @After("execution(* com.yhb.controller.*.*Controller.data(..))")
    public void writeQueryLog(JoinPoint point) throws InterruptedException {
        //设置日志开关
        //从系统参数表中查询是否需要记录访问日志
        SysParams sysParams = sysParamsService.findByParamName("QUERY_LOG_STATUS");
        String logaable = sysParams.getParamValue();
        if (!logaable.equals("1")) {
            log.info("是否记录用户查询日志？" + logaable);
            return;
        }
        Object args[] = point.getArgs();
        //参数倒数第一个 都是
        //获取request
        HttpServletRequest request = (HttpServletRequest) args[0];
        User user = SessionUtil.getCurrentUser(request);
        String ip = request.getRemoteAddr();
        String searchPhrase = args[args.length - 1].toString();

        //只记录有筛选条件的查询日志
        if (searchPhrase != null && !searchPhrase.equals("")) {
            String ctrlName = annotationUtil.getMethodMappingValueCtrlNameByJoinPoint(point);
            Menu menu = menuService.findByAppNameAndMenuTypeAndStatus(ctrlName, "APP", "1");
            //获取到查询的参数 进行封装  记录日志
            AppQueryLog appQueryLog = new AppQueryLog();
            appQueryLog.setAppName(menu.getMenuDesc());
            appQueryLog.setIp(ip);
            appQueryLog.setAccessTime(new Date());
            appQueryLog.setQueryParams(searchPhrase);
            appQueryLog.setUserName(user.getPersonName());
            long begin = System.currentTimeMillis();
            Future<String> test1 = asyncQueryLog.writeQueryLog(appQueryLog);
            while (true) {
                if (test1.isDone())
                    break;
            }
            log.info("end 耗时: " + (System.currentTimeMillis() - begin));
        }
    }
}
