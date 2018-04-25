package com.yhb.service.location;

import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.location.Location;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
@Service
public class LocationSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    LocationRepository locationRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<Location> findByConditions(String searchPhrase, int paramSize,List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return locationRepository.findByLocNameContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<Location> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return locationRepository.findByLocNameContains(array[0], pageable);
    }


}