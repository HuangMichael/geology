package com.yhb.dao.areaProject.projectStat;

import com.yhb.domain.areaProject.projectStat.VAreaProjectStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/5/23 0004.
 * 已建在建项目统计信息数据接口
 */
public interface VProjectStatRepository extends JpaRepository<VAreaProjectStat, Long> {


    /**
     * @return 按照省份查询所有
     */
    List<VAreaProjectStat> findByProv(String prov);


    /**
     * @return 按照市查询所有
     */
    List<VAreaProjectStat> findByCity(String city);


    /**
     * @return 按照县查询所有
     */
    List<VAreaProjectStat> findByDistrict(String district);


}
