package com.yhb.service.farmLand;

import com.yhb.dao.farmLand.FarmLandRepository;
import com.yhb.domain.farmLand.FarmLand;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2016/4/14.
 */

@Service
public class FarmLandSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    FarmLandRepository farmLandRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<FarmLand> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return farmLandRepository.findByLandNoContainsAndLandNameContainsAndIdIn(array[0], array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<FarmLand> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return farmLandRepository.findByLandNoContainsAndLandNameContains(array[0], array[1], pageable);
    }


}
