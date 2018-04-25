package com.yhb.service.geo;

import com.yhb.dao.geo.GeoLayerRepository;
import com.yhb.domain.geoLayer.GeoLayer;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class GeoLayerSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    GeoLayerRepository geoLayerRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<GeoLayer> findByConditions(String searchPhrase, int paramSize,List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return geoLayerRepository.findByLayerNameContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<GeoLayer> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return geoLayerRepository.findByLayerNameContains(array[0], pageable);
    }


}
