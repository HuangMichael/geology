package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectConsRepository;
import com.yhb.dao.areaProject.AreaProjectConsTaskRepository;
import com.yhb.domain.areaProject.AreaProjectCons;
import com.yhb.domain.areaProject.AreaProjectConsTask;
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
 * Created by Administrator on 2017/6/21 0021.
 */
@Service
public class AreaProjectConsSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    AreaProjectConsRepository areaProjectConsRepository;

//    /**
//     * @param searchPhrase
//     * @return 根据多条件关键字进行查询
//     */
//    public List<AreaProjectCons> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
//        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
//        //将开始年份的字符串转换为date格式
//        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
//        //将结束年份的字符串转换为date格式
//        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
//        return areaProjectConsRepository.findByAuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
//                (array[0], array[1], beginDate, endDate, array[4], selectedIds);
//    }
//
//
//    /**
//     * @param searchPhrase
//     * @return 根据多条件关键字进行查询
//     */
//    public Page<AreaProjectCons> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
//        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
//        //将开始年份的字符串转换为date格式
//        Date beginDate = DateUtils.convertStr2DateWithDefault(array[2], "yyyy-MM-dd", true);
//        //将结束年份的字符串转换为date格式
//        Date endDate = DateUtils.convertStr2DateWithDefault(array[3], "yyyy-MM-dd", false);
//        return areaProjectConsRepository.findByAuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
//                (array[0], array[1], beginDate, endDate, array[4], pageable);
//    }
//

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AreaProjectCons> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaProjectConsRepository.findByProject_AuthKeyStartsWithAndStatusContainsAndIdIn
                (array[0], array[7], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaProjectCons> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaProjectConsRepository.findByProject_AuthKeyStartsWithAndStatusContains
                (array[0], array[7], pageable);
    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectConsRepository.selectAllIds();
    }
}
