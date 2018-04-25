package com.yhb.liseners;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * Created by huangbin on 2016/7/18.
 */
public class AppilicationSessionExpire implements ApplicationListener<ContextClosedEvent> {

    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println( "-------------ContextClosedEvent---------------- ");
    }
}
