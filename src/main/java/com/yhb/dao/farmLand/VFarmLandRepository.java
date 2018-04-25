package com.yhb.dao.farmLand;

import com.yhb.domain.farmLand.VFarmLand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
public interface VFarmLandRepository extends JpaRepository<VFarmLand, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VFarmLand a ")
    List<Long> selectAllIds();

    /**
     * @param landNo
     * @param landName
     * @param selectedIds
     * @return 根据多条件复合查询耕地视图信息
     */
    List<VFarmLand> findByLandNoContainsAndLandNameContainsAndIdIn(String landNo, String landName, List<Long> selectedIds);

    /**
     * @param landNo
     * @param landName
     * @param pageable
     * @return 根据多条件复合查询耕地视图信息
     */
    Page<VFarmLand> findByLandNoContainsAndLandNameContains(String landNo, String landName, Pageable pageable);

}

