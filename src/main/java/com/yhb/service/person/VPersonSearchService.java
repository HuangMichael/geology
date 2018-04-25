package com.yhb.service.person;

import com.yhb.dao.person.VPersonRepository;
import com.yhb.domain.person.VPerson;
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
 * <p>
 * 复合查询接口类
 */

@Service
public class VPersonSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    VPersonRepository vPersonRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VPerson> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vPersonRepository.findByDepNameContainsAndPersonNameContainsAndIdIn(array[0],array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VPerson> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vPersonRepository.findByDepNameContainsAndPersonNameContains(array[0],array[1],pageable);
    }


}
