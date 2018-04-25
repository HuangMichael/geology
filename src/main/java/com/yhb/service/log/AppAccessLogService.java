package com.yhb.service.log;

import com.yhb.dao.log.AppAccessLogRepository;
import com.yhb.domain.log.AppAccessLog;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 应用访问日志业务类
 */
@Service
public class AppAccessLogService extends BaseService {

    @Autowired
    AppAccessLogRepository appAccessLogRepository;

    public AppAccessLog save(AppAccessLog appAccessLog) {
        return appAccessLogRepository.save(appAccessLog);
    }
}