package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.TotalPlanDesc;
import com.yhb.domain.areaProject.AreaProjectDesc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 总体规划描述信息接口
 */
public interface TotalPlanDescRepository extends JpaRepository<TotalPlanDesc, Long> {


    /**
     * @param locId
     * @param status
     * @return
     */
    TotalPlanDesc findByLocation_IdAndStatusOrderByIdDesc(Long locId, String status);






}
