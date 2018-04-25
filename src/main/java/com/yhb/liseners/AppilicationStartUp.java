package com.yhb.liseners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by huangbin on 2016/7/18.
 */
public class AppilicationStartUp implements ApplicationListener<ContextRefreshedEvent> {

    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String displayName = applicationContext.getDisplayName();
        System.out.println(displayName + "----------session start------------------- ");
    }
}
