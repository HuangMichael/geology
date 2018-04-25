package com.yhb.liseners;


import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * 监听session的创建与销毁
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("------------sessionCreated----------------- " );
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println( "-------------sessionDestroyed----------------");

    }

}
