package com.yhb.aop;


import com.yhb.domain.log.UserLog;
import com.yhb.domain.sysInfo.SysParams;
import com.yhb.domain.user.User;
import com.yhb.service.sysInfo.SysParamsService;
import com.yhb.service.userLog.UserLogService;
import com.yhb.vo.ReturnObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户日志aop
 */
@Component
@Aspect
@Order(1)
@Slf4j
public class UserLoginAop {
    @Autowired
    UserLogService userLogService;

    @Autowired
    SysParamsService sysParamsService;

    /**
     * @param joinPoint   结合点
     * @param loginResult 登录结果
     */
    @AfterReturning(value = "execution(* com.yhb.controller.LoginController.login(..))", returning = "loginResult")
    public void doAfterReturningAdvice(JoinPoint joinPoint, Object loginResult) {

        //设置日志开关

        //从系统参数表中查询是否需要记录访问日志
        SysParams sysParams = sysParamsService.findByParamName("DATA_TRANSFORM_LOG_STATUS");
        String logaable = sysParams.getParamValue();
        if (!logaable.equals("1")) {
            log.info("是否记录用户登录日志？" + logaable);
            return;
        }

        String userName = (String) joinPoint.getArgs()[1];
        ReturnObject returnObject = (ReturnObject) loginResult;
        boolean result = returnObject.getResult();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String ip = request.getRemoteHost();
        UserLog userLog = new UserLog();
        userLog.setUserName(userName);
        userLog.setLoginIp(ip);
        userLog.setOperation("登录");
        userLog.setOperationTime(new Date());
        userLog.setStatus(result ? "登录成功" : "登录失败");
        userLogService.createUserLog(userLog);
    }


    /**
     * 前置通知，方法调用前被调用
     *
     * @param joinPoint
     */
    @Before(value = "execution(* com.yhb.controller.LoginController.logout(..))")
    public void doBeforeAdvice(JoinPoint joinPoint) {
//        //获取目标方法的参数信息
//        Object[] obj = joinPoint.getArgs();
//        HttpServletRequest request = (HttpServletRequest) obj[0];
//        String ip = request.getRemoteHost();
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//        UserLog userLog = new UserLog();
//        userLog.setUserName(user.getUserName());
//        userLog.setLoginIp(ip);
//        userLog.setOperation("退出");
//        userLog.setOperationTime(new Date());
//        userLog.setStatus("退出系统");
//        userLogService.createUserLog(userLog);
    }
}
