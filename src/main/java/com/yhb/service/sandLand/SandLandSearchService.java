package com.yhb.service.sandLand;

import com.yhb.dao.sandLand.SandLandRepository;
import com.yhb.domain.sandLand.SandLand;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service
public class SandLandSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    SandLandRepository sandLandRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<SandLand> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return sandLandRepository.findBySandNoContainsAndSandNameContainsAndIdIn(array[0],array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<SandLand> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return sandLandRepository.findBySandNoContainsAndSandNameContains(array[0],array[1],pageable);
    }
}
