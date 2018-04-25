package com.yhb.service.devUsing;

import com.yhb.dao.devUsing.AreaDevUsingRepository;
import com.yhb.domain.areaDevUsing.AreaDevUsing;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017-5-24 20:52:06.
 */

@Service
public class AreaDevUsingSearchService extends BaseService implements SortedSearchableWithSelectedIds{

    @Autowired
    AreaDevUsingRepository  areaDevUsingRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字以及用户选择的id列表进行查询
     */
    public List<AreaDevUsing> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaDevUsingRepository.findByTitleContainsAndIdIn(array[0],selectedIds);
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaDevUsing> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaDevUsingRepository.findByTitleContains(array[0], pageable);
    }


}
