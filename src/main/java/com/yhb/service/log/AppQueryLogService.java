package com.yhb.service.log;

import com.yhb.dao.log.AppQueryLogRepository;
import com.yhb.domain.log.AppQueryLog;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 应用查询日志业务类
 */
@Service
public class AppQueryLogService extends BaseService {

    @Autowired
    AppQueryLogRepository appQueryLogRepository;
    public AppQueryLog save(AppQueryLog appQueryLog) {
        return appQueryLogRepository.save(appQueryLog);
    }
}