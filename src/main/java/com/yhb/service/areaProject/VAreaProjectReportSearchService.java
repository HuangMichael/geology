package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectReportRepository;
import com.yhb.domain.areaProject.VAreaProjectReport;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service
public class VAreaProjectReportSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    VAreaProjectReportRepository vAreaProjectReportRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VAreaProjectReport> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vAreaProjectReportRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
                (array[0], array[1], array[2], array[3], array[4], beginDate, endDate, array[7], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VAreaProjectReport> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vAreaProjectReportRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsOrderByImportantDesc
                (array[0], array[1], array[2], array[3], array[4], beginDate, endDate, array[7], pageable);
    }

}
