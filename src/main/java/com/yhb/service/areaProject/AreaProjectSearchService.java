package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectRepository;
import com.yhb.domain.areaProject.AreaProject;
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
 * Created by huangbin  on 2017-5-23 09:27:25
 * 项目信息复合查询业务类
 */

@Service
public class AreaProjectSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    AreaProjectRepository areaProjectRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AreaProject> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[4], "yyyy-MM-dd", false);
        return areaProjectRepository.findByAuthKeyStartsWithAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdIn(
                array[0], array[1], array[2], beginYear, endYear, ConstantUtils.STATUS_AUTHORIZED, selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaProject> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[4], "yyyy-MM-dd", false);
        return areaProjectRepository.findByAuthKeyStartsWithAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains(
                array[0], array[1], array[2], beginYear, endYear, ConstantUtils.STATUS_AUTHORIZED, pageable);
    }

    /**
     * @param searchPhrase
     * @param paramSize
     * @return 根据多条件关键字进行查询符合条件的项目的id列表
     */
    public List<Long> getIdListByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[4], "yyyy-MM-dd", false);
        List<AreaProject> areaProjectList = areaProjectRepository.findByAuthKeyStartsWithAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
                (array[0], array[1], array[2], beginYear, endYear, ConstantUtils.STATUS_AUTHORIZED);
        if (areaProjectList == null || areaProjectList.size() == 0) {
            return null;
        }
        List<Long> resList = new ArrayList<>();
        for (int i = 0; i < areaProjectList.size(); i++) {
            resList.add(areaProjectList.get(i).getId());
        }
        System.out.println("==areaProject===resList======" + resList);
        return resList;
    }
}
