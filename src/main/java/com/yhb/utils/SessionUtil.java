package com.yhb.utils;


import com.yhb.domain.user.User;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by HUANGBIN on 2017/6/14 0018.
 * 获取当前的session
 */
@Slf4j
public class SessionUtil {
    /**
     * 获取当前上下文路径
     *
     * @param request
     * @return
     */
    public static String getContextPath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
        String serverRealPath = application.getRealPath("/");


        log.info("serverRealPath------------------" + serverRealPath);
        return serverRealPath;
    }


    /**
     * 获取当前用户信息
     *
     * @param request
     * @return
     */
    public static User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }
}
