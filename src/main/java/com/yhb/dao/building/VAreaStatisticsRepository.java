package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.VAreaStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */
public interface VAreaStatisticsRepository extends JpaRepository<VAreaStatistics, Long> {

    /**
     * @param id
     * @return
     */
    VAreaStatistics findById(Long id);

    /**
     * @param areaNoList
     * @return
     */
    List<VAreaStatistics> findByAreaNoIn(List<String> areaNoList);
}
