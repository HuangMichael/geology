package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.VProvPlan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/9/7.
 * 总体规划按照省份统计 下钻市
 */
public interface VProvPlanRepository extends JpaRepository<VProvPlan, Long> {


}
