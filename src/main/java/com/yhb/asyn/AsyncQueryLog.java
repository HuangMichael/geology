package com.yhb.asyn;

import com.yhb.domain.log.AppQueryLog;
import com.yhb.service.log.AppQueryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Created by huangbin on 2017/9/8.
 */

@Component
@Slf4j
@SuppressWarnings(value = "unchecked")
public class AsyncQueryLog {
    /**
     * @Async 所修饰的函数不要定义为static类型，否则异步调用不会生效
     * 这里通过返回Future<T>来返回异步调用的结果，实现异步回调
     */
    @Autowired
    AppQueryLogService appQueryLogService;

    @Async
    public Future<String> writeQueryLog(AppQueryLog appQueryLog) throws InterruptedException {
        //将用户查询日志写入数据库
        appQueryLog = appQueryLogService.save(appQueryLog);
        return new AsyncResult("save query log ?" + (appQueryLog != null));
    }
}