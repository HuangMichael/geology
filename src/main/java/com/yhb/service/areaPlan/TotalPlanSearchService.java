package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.TotalPlanRepository;
import com.yhb.domain.areaPlan.TotalPlan;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
@Service
public class TotalPlanSearchService  extends BaseService implements SortedSearchable {
    @Autowired
    TotalPlanRepository totalPlanRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<TotalPlan> findByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return totalPlanRepository.findByPlanDescContains(array[0]);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<TotalPlan> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return totalPlanRepository.findByPlanDescContains(array[0],pageable);
    }
}
