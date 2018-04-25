package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.VProvBuilding;
import com.yhb.domain.areaBuilding.VcityBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017-08-06 12:25:22
 * 查询城市围垦面积
 */
public interface VProvBuildingRepository extends JpaRepository<VProvBuilding, Long> {


}
