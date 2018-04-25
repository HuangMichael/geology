package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.VProv135Plan;
import com.yhb.domain.areaPlan.VProvPlan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/9/7.
 * 总体规划按照省份统计 下钻市
 */
public interface VProv135PlanRepository extends JpaRepository<VProv135Plan, Long> {


}
