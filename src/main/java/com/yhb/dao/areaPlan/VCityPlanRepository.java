package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.VCityPlan;
import com.yhb.domain.areaPlan.VProvPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017/9/7.
 * 总体规划按照市统计 下钻县
 */
public interface VCityPlanRepository extends JpaRepository<VCityPlan, Long> {

    /**
     * @param parentId
     * @return
     */
    List<VCityPlan> findByParentId(Long parentId);
}
