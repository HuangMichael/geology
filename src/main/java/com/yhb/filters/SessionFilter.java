package com.yhb.filters;


import com.yhb.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by HUANGBIN on 2016/3/1 0001.
 */
@Component("sessionFilter")
@WebFilter
@Order(1)
public class SessionFilter implements javax.servlet.Filter {


    Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器----------------------init");
    }

    /**
     * //doFilter(ServletRequest request, ServletResponse response,FilterChain chain): 实现过滤功能，该方法就是对每个请求及响应增加的额外处理。
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        String url = request.getRequestURI();
        HttpSession httpSession = request.getSession(true);
        logger.info("url:" + url);
        if (url.equals("/login") || url.equals("/") || url.endsWith("index.jsp") || url.contains("download")) {
            filterChain.doFilter(request, response);
        } else if (url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".gif") || url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".ico") || url.endsWith(".woff") || url.endsWith(".woff2") || url.contains(".ttf")) {
            filterChain.doFilter(request, response);
        } else {
            if (httpSession != null) {
                User user = (User) httpSession.getAttribute("user");
                if (user != null) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendRedirect("/");
                }
            } else {
                response.sendRedirect("/");
            }
        }
    }

    @Override
    public void destroy() {
        logger.info("过滤器----------------------destroy");
    }
}

