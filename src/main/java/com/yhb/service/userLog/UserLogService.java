package com.yhb.service.userLog;

import com.yhb.dao.userLog.UserLogRepository;
import com.yhb.domain.log.UserLog;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangbin on 2017-08-17 14:14:07
 * 用户日志类
 */
@Service
public class UserLogService extends BaseService {


    @Autowired
    UserLogRepository userLogRepository;

    /**
     * @param userLog
     * @return 创建用户日志
     */
    public UserLog createUserLog(UserLog userLog) {
        return userLogRepository.save(userLog);
    }


}
