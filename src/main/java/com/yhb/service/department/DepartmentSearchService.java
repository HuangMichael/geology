package com.yhb.service.department;

import com.yhb.dao.department.DepartmentRepository;
import com.yhb.domain.department.Department;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017/5/17.
 * 部门信息复合查询业务类
 */

@Service
public class DepartmentSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    DepartmentRepository departmentRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<Department> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return departmentRepository.findByDepNameContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<Department> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return departmentRepository.findByDepNameContains(array[0], pageable);
    }


}
