package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.VcityBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017-08-06 12:25:22
 * 查询城市围垦面积
 */
public interface VcityBuildingRepository extends JpaRepository<VcityBuilding, Long> {

    List<VcityBuilding> findByParentId(Long parentId);

}
