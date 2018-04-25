
package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.NatureReserveExperimentRepository;
import com.yhb.domain.natureReserve.NatureReserveExperiment;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijina  on 2017-6-22
 */

@Service
public class NatureReserveExperimentSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    NatureReserveExperimentRepository experimentRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<NatureReserveExperiment> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return experimentRepository.findByExperimentNoContainsAndExperimentNameContainsAndIdIn(array[0], array[1], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<NatureReserveExperiment> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return experimentRepository.findByExperimentNoContainsAndExperimentNameContains(array[0], array[1], pageable);
    }


}

