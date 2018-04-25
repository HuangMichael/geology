package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VProjectCityPlan;
import com.yhb.domain.areaProject.VProjectProvPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/23 0004.
 * 各市项目统计信息数据接口
 */
public interface VProjectCityRepository extends JpaRepository<VProjectCityPlan, Long> {


    /**
     * @param cityName
     * @return 城市  根据城市名称查询
     */
    List<VProjectCityPlan> findByCityName(String cityName);

}
