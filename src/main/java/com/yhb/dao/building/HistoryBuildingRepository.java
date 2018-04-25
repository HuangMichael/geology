package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.HistoryBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/8 0008.
 * 围垦信息业务类
 */
public interface HistoryBuildingRepository extends JpaRepository<HistoryBuilding, Long> {



    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from HistoryBuilding  b")
    Long findMaxId();


    /**
     * @param period
     * @return
     */
    List<HistoryBuilding> findByPeriodOrderBySortNo(String period);

}
