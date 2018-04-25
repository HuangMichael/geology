package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectConsTaskRepository;
import com.yhb.domain.areaProject.AreaProjectConsTask;
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
 * Created by Administrator on 2017/6/20 0020.
 */
@Service
public class AreaProjectConsTaskSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    AreaProjectConsTaskRepository areaProjectConsTaskRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AreaProjectConsTask> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
        return areaProjectConsTaskRepository.findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
                (array[0], array[1], beginDate, endDate, array[4], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaProjectConsTask> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
        return areaProjectConsTaskRepository.findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
                (array[0], array[1], beginDate, endDate, array[4], pageable);
    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectConsTaskRepository.selectAllIds();
    }
}
