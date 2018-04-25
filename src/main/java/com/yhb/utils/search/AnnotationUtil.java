package com.yhb.utils.search;

import com.yhb.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * Created by huangbin on 2017/9/8.
 */

@SuppressWarnings(value = "unchecked")
@Component
public class AnnotationUtil {


    /**
     * @param point
     * @return
     */
    public String getMethodMappingValueByJoinPoint(JoinPoint point) {
        String classAnno = getMethodMappingValueCtrlNameByJoinPoint(point);
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();

        String methodAnno = "";
        boolean hasRequestMapping = targetMethod.isAnnotationPresent(RequestMapping.class);
        if (hasRequestMapping) {
            //得到注解
            RequestMapping requestMapping = targetMethod.getAnnotation(RequestMapping.class);
            //输出注解属性
            String values[] = requestMapping.value();
            methodAnno = values[0];
        }
        return classAnno + methodAnno;
    }


    /**
     * @param point
     * @return
     */
    public String getMethodMappingValueCtrlNameByJoinPoint(JoinPoint point) {
        Class clazz = point.getTarget().getClass();
        boolean classHasRequestMapping = clazz.isAnnotationPresent(RequestMapping.class);
        String classAnno = "";
        if (classHasRequestMapping) {
            RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            //输出注解属性
            String values[] = requestMapping.value();
            classAnno = values[0];
            classAnno = classAnno.replace("/", "");
        }
        return classAnno;
    }


    /**
     * @param clazz
     * @return
     */
    public String getAppPage(Class clazz, String suffix) {
        boolean classHasRequestMapping = clazz.isAnnotationPresent(RequestMapping.class);
        String classAnno = "";
        if (classHasRequestMapping) {
            RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            //输出注解属性
            String values[] = requestMapping.value();
            classAnno = values[0];
            classAnno = classAnno.replace("/", "");
        }
        String url = "/" + StringUtils.lowerCaseCamel(classAnno) + "/" + suffix;
        return url;
    }


}
