package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.ThirteenFivePlanDesc;
import com.yhb.domain.areaPlan.TotalPlanDesc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 十三五规划描述信息接口
 */
public interface ThirteenFivePlanDescRepository extends JpaRepository<ThirteenFivePlanDesc, Long> {


    /**
     * @param locId
     * @param status
     * @return
     */
    ThirteenFivePlanDesc findByLocation_IdAndStatusOrderByIdDesc(Long locId, String status);




}
