package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.VTotalPlanRepository;
import com.yhb.domain.areaPlan.VTotalPlan;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
@Service
public class VTotalPlanSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VTotalPlanRepository vTotalPlanRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VTotalPlan> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vTotalPlanRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdIn
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7], selectedIds);//array[7]为审核状态
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VTotalPlan> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);

        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vTotalPlanRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7], pageable);
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询符合条件的所有总体规划id列表
     */
    public List<Long> getIdListByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        List<VTotalPlan> vTotalPlanList = vTotalPlanRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7]);
        if (vTotalPlanList == null || vTotalPlanList.size() == 0) {
            return null;
        }
        List<Long> resList = new ArrayList<>();
        for (int i = 0; i < vTotalPlanList.size(); i++) {
            resList.add(vTotalPlanList.get(i).getId());
        }
        System.out.println("==vTotalPlan===resList======" + resList);
        return resList;
    }
}
