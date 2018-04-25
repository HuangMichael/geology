package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.ThirteenFivePlanRepository;
import com.yhb.domain.areaPlan.ThirteenFivePlan;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public class ThirteenFivePlanSearchService extends BaseService implements SortedSearchable {
    @Autowired
    ThirteenFivePlanRepository thirteenFivePlanRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<ThirteenFivePlan> findByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return thirteenFivePlanRepository.findByPlanDescContains(array[0]);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<ThirteenFivePlan> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return thirteenFivePlanRepository.findByPlanDescContains(array[0],pageable);
    }
}
