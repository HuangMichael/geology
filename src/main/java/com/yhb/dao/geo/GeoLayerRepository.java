package com.yhb.dao.geo;

import com.yhb.domain.geoLayer.GeoLayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 * 图层信息数据接口
 */
public interface GeoLayerRepository extends JpaRepository<GeoLayer, Long> {
    /**
     * @param LayerName
     * @return
     */
    List<GeoLayer> findByLayerNameContainsAndIdIn(String LayerName, List<Long> selectedIds);

    /**
     * @param LayerName
     * @param pageable  可分页参数
     * @return
     */
    Page<GeoLayer> findByLayerNameContains(String LayerName, Pageable pageable);

    /**
     * @param LayerName
     * @return
     */
    List<GeoLayer> findByLayerNameContains(String LayerName);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from GeoLayer a ")
    List<Long> selectAllIds();

}
