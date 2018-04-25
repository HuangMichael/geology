package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.NatureReserveBufferRepository;
import com.yhb.domain.natureReserve.NatureReserveBuffer;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017-5-20 14:00:34
 */

@Service
public class NatureReserveBufferSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    NatureReserveBufferRepository bufferRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<NatureReserveBuffer> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return bufferRepository.findByBufferNoContainsAndBufferNameContainsAndIdIn(array[0], array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<NatureReserveBuffer> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return bufferRepository.findByBufferNoContainsAndBufferNameContains(array[0], array[1], pageable);
    }


}
