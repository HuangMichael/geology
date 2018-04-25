package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProjectDesc;
import com.yhb.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 项目描述信息接口
 */
public interface AreaProjectDescRepository extends JpaRepository<AreaProjectDesc, Long> {


    /**
     * @param locId
     * @param status
     * @return
     */
    AreaProjectDesc findByLocation_IdAndStatusOrderByIdDesc(Long locId, String status);

}
