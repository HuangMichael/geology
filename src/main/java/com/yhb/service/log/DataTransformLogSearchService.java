package com.yhb.service.log;

import com.yhb.dao.log.DataTransformLogRepository;
import com.yhb.domain.log.DataTransformLog;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by huangbin on 2017/5/4 0004.
 * 数据导入导出日志业务类
 */
@Service
public class DataTransformLogSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    DataTransformLogRepository dataTransformLogRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<DataTransformLog> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return dataTransformLogRepository.findByUserNameContainsAndIdIn(array[0], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<DataTransformLog> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return dataTransformLogRepository.findByUserNameContains(array[0], pageable);
    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return dataTransformLogRepository.selectAllIds();
    }
}