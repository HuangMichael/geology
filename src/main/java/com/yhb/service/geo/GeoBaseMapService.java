package com.yhb.service.geo;

import com.yhb.dao.geo.GeoBaseMapRepository;
import com.yhb.dao.geo.GeoLayerRepository;
import com.yhb.domain.geoBaseMap.GeoBaseMap;
import com.yhb.domain.geoLayer.GeoLayer;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2017-08-18 09:31:26
 */
@Service
@CacheConfig
public class GeoBaseMapService extends BaseService {
    @Autowired
    GeoBaseMapRepository geoBaseMapRepository;

    @Autowired
    CommonDataService commonDataService;


    /**
     * @param id
     * @return 根据id查询底图信息
     */
    public GeoBaseMap findById(Long id) {
        return geoBaseMapRepository.findOne(id);
    }


    /**
     * @return 查询所有底图信息
     */
    @Cacheable(value = "baseMaps", key = "'baseMaps'")
    public List<GeoBaseMap> findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "sortNo");
        return geoBaseMapRepository.findAll(sort);
    }
}
