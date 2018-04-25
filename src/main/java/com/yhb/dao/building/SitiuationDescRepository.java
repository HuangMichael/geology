package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.SituationDesc;
import com.yhb.domain.areaPlan.TotalPlanDesc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 围垦现状描述信息接口
 */
public interface SitiuationDescRepository extends JpaRepository<SituationDesc, Long> {


    /**
     * @param locId
     * @param status
     * @return
     */
    SituationDesc findByLocation_IdAndStatusOrderByIdDesc(Long locId, String status);

}
