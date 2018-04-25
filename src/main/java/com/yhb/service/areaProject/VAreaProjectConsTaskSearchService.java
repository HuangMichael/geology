package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectConsTaskRepository;
import com.yhb.domain.areaProject.VAreaProjectConsTask;
import com.yhb.service.base.BaseService;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
@Service
public class VAreaProjectConsTaskSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VAreaProjectConsTaskRepository vAreaProjectConsTaskRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VAreaProjectConsTask> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vAreaProjectConsTaskRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
                (array[0], array[1], array[2], array[3], array[4], beginDate, endDate, array[7], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VAreaProjectConsTask> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vAreaProjectConsTaskRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsOrderByImportantDesc
                (array[0], array[1], array[2], array[3], array[4], beginDate, endDate, array[7], pageable);
    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return vAreaProjectConsTaskRepository.selectAllIds();
    }
}
