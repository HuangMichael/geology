package com.yhb.service.beach;

import com.yhb.dao.beach.VBeachRepository;
import com.yhb.domain.beach.VBeach;
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
public class VBeachSearchService  extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VBeachRepository vBeachRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VBeach> findByConditions(String searchPhrase, int paramSize,List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vBeachRepository.findByBeachNoContainsAndBeachNameContainsAndIdIn(array[0],array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VBeach> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vBeachRepository.findByBeachNoContainsAndBeachNameContains(array[0],array[1],pageable);
    }
}
