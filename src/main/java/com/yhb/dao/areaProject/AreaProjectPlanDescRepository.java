package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProjectPlanDesc;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 项目规划描述信息接口
 */
public interface AreaProjectPlanDescRepository extends JpaRepository<AreaProjectPlanDesc, Long> {
    /**
     * @param locId  位置id
     * @param status 状态
     * @return 根据位置和状态查询
     */
    AreaProjectPlanDesc findByLocation_IdAndStatusOrderByIdDesc(Long locId, String status);

}
