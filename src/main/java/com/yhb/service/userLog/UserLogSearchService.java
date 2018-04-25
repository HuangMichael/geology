package com.yhb.service.userLog;

import com.yhb.dao.userLog.UserLogRepository;
import com.yhb.domain.log.UserLog;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
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
public class UserLogSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    UserLogRepository userLogRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<UserLog> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return userLogRepository.findByUserNameContainsAndIdInOrderByOperationTime(array[0], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<UserLog> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return userLogRepository.findByUserNameContainsOrderByOperationTime(array[0], pageable);
    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return userLogRepository.selectAllIds();
    }
}
