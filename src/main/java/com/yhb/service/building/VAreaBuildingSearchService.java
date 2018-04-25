package com.yhb.service.building;

import com.yhb.dao.building.VAreaBuildingRepository;
import com.yhb.domain.areaBuilding.VAreaBuilding;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangbin 2017/paramSize-2/22.
 * 围垦信息复合查询业务类
 */
@Service
public class VAreaBuildingSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    VAreaBuildingRepository vAreaBuildingRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VAreaBuilding> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);

        return vAreaBuildingRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdIn
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7], selectedIds);//array[7]为审核状态，已审核还是未审核
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VAreaBuilding> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);

        return vAreaBuildingRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7], pageable);//array[7]为审核状态，已审核还是未审核
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询符合条件的所有区块编号列表
     */
    public List<String> getAreaNoListByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        List<VAreaBuilding> vAreaBuildingList = vAreaBuildingRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7]);//array[7]为审核状态，已审核还是未审核
        if (vAreaBuildingList == null || vAreaBuildingList.size() == 0) {
            return null;
        }
        List<String> resList = new ArrayList<>();
        for (int i = 0; i < vAreaBuildingList.size(); i++) {
            resList.add(vAreaBuildingList.get(i).getAreaNo());
        }
        System.out.println("==vAreaBuilding===resList======" + resList);
        return resList;
    }

}
