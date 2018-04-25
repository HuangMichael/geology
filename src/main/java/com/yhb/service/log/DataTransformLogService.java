package com.yhb.service.log;

import com.yhb.dao.log.DataTransformLogRepository;
import com.yhb.domain.log.DataTransformLog;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by huangbin on 2017/5/4 0004.
 * 数据导入导出日志业务类
 */
@Service
public class DataTransformLogService extends BaseService {

    @Autowired
    DataTransformLogRepository dataTransformLogRepository;

    /**
     * @param dataTransformLog
     * @return
     */
    public DataTransformLog save(DataTransformLog dataTransformLog) {
        return dataTransformLogRepository.save(dataTransformLog);
    }
}