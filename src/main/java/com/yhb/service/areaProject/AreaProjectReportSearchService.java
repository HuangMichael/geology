package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectReportRepository;
import com.yhb.domain.areaProject.AreaProjectReport;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin  on 2017-5-22 09:27:25
 * 可行性研究报告信息复合查询业务类
 */

@Service
public class AreaProjectReportSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    AreaProjectReportRepository areaProjectReportRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AreaProjectReport> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
        return areaProjectReportRepository.findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
                (array[0], array[1], beginDate, endDate, ConstantUtils.STATUS_YES, selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaProjectReport> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
        return areaProjectReportRepository.findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
                (array[0], array[1], beginDate, endDate, ConstantUtils.STATUS_YES, pageable);
    }


}
