package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectAcceptRepository;
import com.yhb.domain.areaProject.AreaProjectAccept;
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
 * 验收信息复合查询业务类
 */

@Service
public class AreaProjectAcceptSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    AreaProjectAcceptRepository areaProjectAcceptRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AreaProjectAccept> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
//        //将开始年份的字符串转换为date格式
//        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
//        //将结束年份的字符串转换为date格式
//        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
        return areaProjectAcceptRepository.findByProject_AuthKeyStartsWithAndStatusContainsAndIdIn
                (array[0], array[7], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaProjectAccept> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
//        //将开始年份的字符串转换为date格式
//        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
//        //将结束年份的字符串转换为date格式
//        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
        return areaProjectAcceptRepository.findByProject_AuthKeyStartsWithAndStatusContains
                (array[0], array[7], pageable);
    }


}
