package com.yhb.service.coastLine;

import com.yhb.dao.coastLine.CoastLineRepository;
import com.yhb.domain.coastline.CoastLine;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017-5-20 11:35:57
 */

@Service
public class CoastLineSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    CoastLineRepository coastLineRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<CoastLine> findByConditions(String searchPhrase, int paramSize,List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return coastLineRepository.findByLineNoContainsAndLineNameContainsAndIdIn(array[0], array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<CoastLine> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return coastLineRepository.findByLineNoContainsAndLineNameContains(array[0], array[1], pageable);
    }
}
