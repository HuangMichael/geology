package com.yhb.service.log;

import com.yhb.dao.log.AppQueryLogRepository;
import com.yhb.domain.log.AppQueryLog;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017/5/17.
 * <p>
 * 复合查询接口类
 */

@Service
public class AppQueryLogSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    AppQueryLogRepository appQueryLogRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AppQueryLog> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return appQueryLogRepository.findByUserNameContainsAndIdIn(array[0], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AppQueryLog> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return appQueryLogRepository.findByUserNameContains(array[0], pageable);
    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return appQueryLogRepository.selectAllIds();
    }
}
