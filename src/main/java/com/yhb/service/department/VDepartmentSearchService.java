package com.yhb.service.department;

import com.yhb.dao.department.VDepartmentRepository;
import com.yhb.domain.department.VDepartment;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
@Service
public class VDepartmentSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    VDepartmentRepository vDepartmentRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VDepartment> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vDepartmentRepository.findByDepNameContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VDepartment> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vDepartmentRepository.findByDepNameContains(array[0], pageable);
    }

}
