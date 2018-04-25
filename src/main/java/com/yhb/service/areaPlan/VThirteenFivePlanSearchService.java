package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.VThirteenFivePlanRepository;
import com.yhb.domain.areaPlan.VThirteenFivePlan;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
@Service
public class VThirteenFivePlanSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VThirteenFivePlanRepository vThirteenFivePlanRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VThirteenFivePlan> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vThirteenFivePlanRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContainsAndIdIn
                (array[0], array[1], array[2], array[3], array[4], array[5], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VThirteenFivePlan> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vThirteenFivePlanRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], array[5], pageable);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询符合条件的所有十三五规划id列表
     */
    public List<Long> getIdListByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        List<VThirteenFivePlan> vThirteenFivePlanList = vThirteenFivePlanRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], array[5]);
        if (vThirteenFivePlanList == null || vThirteenFivePlanList.size() == 0) {
            return null;
        }
        List<Long> resList = new ArrayList<>();
        for (int i = 0; i < vThirteenFivePlanList.size(); i++) {
            resList.add(vThirteenFivePlanList.get(i).getId());
        }
        System.out.println("==vThirteenFivePlan===resList======" + resList);
        return resList;
    }
}
