package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.NatureReserveAreaRepository;
import com.yhb.domain.natureReserve.NatureReserveArea;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017-5-20 14:00:34
 * huangbin
 */

@Service
public class NatureReserveAreaSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    NatureReserveAreaRepository areaRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<NatureReserveArea> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaRepository.findByAreaNoContainsAndAreaNameContainsAndIdIn(array[0], array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<NatureReserveArea> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaRepository.findByAreaNoContainsAndAreaNameContains(array[0], array[1], pageable);
    }


}
