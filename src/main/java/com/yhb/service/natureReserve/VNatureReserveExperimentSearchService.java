package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.VNatureReserveExperimentRepository;
import com.yhb.domain.natureReserve.VNatureReserveExperiment;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service
public class VNatureReserveExperimentSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VNatureReserveExperimentRepository vNatureReserveExperimentRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VNatureReserveExperiment> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vNatureReserveExperimentRepository.findByExperimentNoContainsAndExperimentNameContainsAndIdIn(array[0],array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VNatureReserveExperiment> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vNatureReserveExperimentRepository.findByExperimentNoContainsAndExperimentNameContains(array[0],array[1],pageable);
    }
}