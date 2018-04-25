package com.yhb.dao.natureReserve;

import com.yhb.domain.natureReserve.VNatureReserveExperiment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
public interface VNatureReserveExperimentRepository extends JpaRepository<VNatureReserveExperiment, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VNatureReserveExperiment a ")
    List<Long> selectAllIds();

    /**
     * @param experimentNo
     * @param experimentName
     * @param selectedIds
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    List<VNatureReserveExperiment> findByExperimentNoContainsAndExperimentNameContainsAndIdIn(String experimentNo, String experimentName, List<Long> selectedIds);

    /**
     * @param experimentNo
     * @param experimentName
     * @param pageable
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    Page<VNatureReserveExperiment> findByExperimentNoContainsAndExperimentNameContains(String experimentNo, String experimentName, Pageable pageable);

}
