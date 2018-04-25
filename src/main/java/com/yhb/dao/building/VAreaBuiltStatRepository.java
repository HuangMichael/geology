package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.VAreaBuiltStat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/8 0008.
 * 区块已围围垦信息业务类
 */
public interface VAreaBuiltStatRepository extends JpaRepository<VAreaBuiltStat, Long> {


    /**
     * @param areaId 区块信息id
     * @return 根据区块查询
     */
    VAreaBuiltStat findByArea_Id(Long areaId);

}
