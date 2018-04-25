package com.yhb.dao.areaProject.projectStat;

import com.yhb.domain.areaProject.projectStat.VAreaProjectPlanStat;
import com.yhb.domain.areaProject.projectStat.VAreaProjectStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/23 0004.
 * 全省项目统计信息数据接口
 */
public interface VProjectPlanStatRepository extends JpaRepository<VAreaProjectPlanStat, Long> {


    /**
     * @return 按照省份查询所有
     */
    List<VAreaProjectPlanStat> findByProv(String prov);


    /**
     * @return 按照市查询所有
     */
    List<VAreaProjectPlanStat> findByCity(String city);


    /**
     * @return 按照县查询所有
     */
    List<VAreaProjectPlanStat> findByDistrict(String district);


}
