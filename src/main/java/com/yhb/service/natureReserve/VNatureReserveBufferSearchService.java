package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.VNatureReserveBufferRepository;
import com.yhb.domain.natureReserve.VNatureReserveBuffer;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service
public class VNatureReserveBufferSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VNatureReserveBufferRepository vNatureReserveBufferRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VNatureReserveBuffer> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vNatureReserveBufferRepository.findByBufferNoContainsAndBufferNameContainsAndIdIn(array[0],array[1],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VNatureReserveBuffer> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vNatureReserveBufferRepository.findByBufferNoContainsAndBufferNameContains(array[0],array[1],pageable);
    }
}