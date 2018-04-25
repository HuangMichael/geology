package com.yhb.asyn;

import com.yhb.domain.log.AppAccessLog;
import com.yhb.service.log.AppAccessLogService;
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
public class AsyncAccessLog {
    /**
     * @Async 所修饰的函数不要定义为static类型，否则异步调用不会生效
     * 这里通过返回Future<T>来返回异步调用的结果，实现异步回调
     */
    @Autowired
    AppAccessLogService appAccessLogService;

    @Async
    public Future<String> writeAccessLog(AppAccessLog appAccessLog) throws InterruptedException {
        //将用户查询日志写入数据库
        appAccessLog = appAccessLogService.save(appAccessLog);
        return new AsyncResult("save access log ?" + (appAccessLog != null));
    }
}