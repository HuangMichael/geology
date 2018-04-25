package com.yhb.service.farmLand;

import com.yhb.dao.farmLand.VFarmLandRepository;
import com.yhb.domain.farmLand.VFarmLand;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
@Service
public class VFarmLandSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    
    @Autowired
    VFarmLandRepository vFarmLandRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VFarmLand> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vFarmLandRepository.findByLandNoContainsAndLandNameContainsAndIdIn(array[0], array[1], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VFarmLand> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vFarmLandRepository.findByLandNoContainsAndLandNameContains(array[0], array[1], pageable);
    }
}
