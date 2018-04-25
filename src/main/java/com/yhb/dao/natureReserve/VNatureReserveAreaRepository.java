package com.yhb.dao.natureReserve;


import com.yhb.domain.natureReserve.VNatureReserveArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
public interface VNatureReserveAreaRepository extends JpaRepository<VNatureReserveArea, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VNatureReserveArea a ")
    List<Long> selectAllIds();

    /**
     * @param areaNo
     * @param areaName
     * @param selectedIds
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    List<VNatureReserveArea> findByAreaNoContainsAndAreaNameContainsAndIdIn(String areaNo, String areaName, List<Long> selectedIds);

    /**
     * @param areaNo
     * @param areaName
     * @param pageable
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    Page<VNatureReserveArea> findByAreaNoContainsAndAreaNameContains(String areaNo, String areaName, Pageable pageable);

}