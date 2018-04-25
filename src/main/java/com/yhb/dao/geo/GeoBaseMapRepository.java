package com.yhb.dao.geo;

import com.yhb.domain.geoBaseMap.GeoBaseMap;
import com.yhb.domain.geoLayer.GeoLayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by huangbin on 2017-08-18 09:31:02
 * 底图图层信息数据接口
 */
public interface GeoBaseMapRepository extends JpaRepository<GeoBaseMap, Long>, CrudRepository<GeoBaseMap, Long>{


    /**
     * @param sort
     * @return
     */
    @Override
    List<GeoBaseMap> findAll(Sort sort);
}
