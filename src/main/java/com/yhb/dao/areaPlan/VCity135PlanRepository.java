package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.VCity135Plan;
import com.yhb.domain.areaPlan.VCityPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/9/7.
 * 总体规划按照市统计 下钻县
 */
public interface VCity135PlanRepository extends JpaRepository<VCity135Plan, Long> {

    /**
     * @param parentId
     * @return
     */
    List<VCity135Plan> findByParentId(Long parentId);
}
