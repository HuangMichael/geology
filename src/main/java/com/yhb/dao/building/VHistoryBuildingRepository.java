package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.VHistoryBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/9/14 0008.
 * 历史围垦信息业务类
 */
public interface VHistoryBuildingRepository extends JpaRepository<VHistoryBuilding, Long> {


    /**
     * @param period 时期
     * @return
     */
    List<VHistoryBuilding> findByPeriod(String period);


    /**
     * @param period  时期
     * @param locName 位置
     * @return
     */
    List<VHistoryBuilding> findByPeriodAndLocName(String period, String locName);

    /**
     * 查询所有的围垦信息，按照年份求和
     *
     * @return
     */
    @Query("select period, sum(vhb.buildSize) as buildSize from VHistoryBuilding vhb group by period order by period")
    List<Object> findAllGroupByPeriod();
}
