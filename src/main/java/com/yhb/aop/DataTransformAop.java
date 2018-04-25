package com.yhb.aop;


import com.yhb.asyn.AsyncDataTransformLog;
import com.yhb.domain.log.DataTransformLog;
import com.yhb.domain.sysInfo.SysParams;
import com.yhb.domain.user.User;
import com.yhb.service.sysInfo.SysParamsService;
import com.yhb.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * 数据导入导出日志aop
 */
@Component
@Aspect
@Order(4)
@Slf4j
public class DataTransformAop {

    @Autowired
    AsyncDataTransformLog asyncDataTransformLog;
    @Autowired
    SysParamsService  sysParamsService;

    /**
     * @param joinPoint 结合点
     * @throws InterruptedException
     * @throws IllegalAccessException
     */
    @After(value = "execution(* com.yhb.controller.*.*Controller.exportExcel(..))")
    public void doAfterAdvice(JoinPoint joinPoint) throws InterruptedException, IllegalAccessException {


        //设置日志开关

        //从系统参数表中查询是否需要记录访问日志
        SysParams sysParams = sysParamsService.findByParamName("DATA_TRANSFORM_LOG_STATUS");
        String logaable = sysParams.getParamValue();
        if (!logaable.equals("1")) {

            log.info("是否记录数据传输日志？" + logaable);
            return;
        }


        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
        User user = SessionUtil.getCurrentUser(request);
        Object object = joinPoint.getTarget();

        Class clazz = object.getClass();
        Field field;
        String docName = "";
        try {
            field = clazz.getDeclaredField("objectName");
            field.setAccessible(true);
            docName = field.get(object).toString();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        DataTransformLog dataTransformLog = new DataTransformLog();
        dataTransformLog.setUserName(user.getPersonName() + "(" + user.getUserName() + ")");
        dataTransformLog.setFileName(docName);
        dataTransformLog.setOperateTime(new Date());
        dataTransformLog.setOperateType("导出");
        asyncDataTransformLog.writeDataTransformLog(dataTransformLog);
    }
}
