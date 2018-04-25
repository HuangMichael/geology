package com.yhb.service.building;

import com.yhb.dao.building.HistoryRepository;
import com.yhb.domain.history.History;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by llm on 2017/5/16 0016.
 */
@Service
public class HistorySearchService extends BaseService implements SortedSearchable {
    @Autowired
    HistoryRepository historyRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字以及用户选择的id列表进行查询
     */
    public List<History> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return historyRepository.findByTitleContainsAndIdIn(array[0], selectedIds);
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<History> findByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return historyRepository.findByTitleContains(array[0]);
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<History> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return historyRepository.findByTitleContains(array[0], pageable);
    }


}
