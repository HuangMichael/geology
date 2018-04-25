package com.yhb.liseners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by huangbin on 2016/7/18.
 */
public class AppilicationStopped implements ApplicationListener<ContextStoppedEvent> {

    public void onApplicationEvent(ContextStoppedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String displayName = applicationContext.getDisplayName();
        System.out.println(displayName + "----------session stop------------------- ");
    }
}
