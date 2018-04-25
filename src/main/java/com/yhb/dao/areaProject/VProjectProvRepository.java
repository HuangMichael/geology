package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VProjectProvPlan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/23 0004.
 * 全省项目统计信息数据接口
 */
public interface VProjectProvRepository extends JpaRepository<VProjectProvPlan, Long> {

}
