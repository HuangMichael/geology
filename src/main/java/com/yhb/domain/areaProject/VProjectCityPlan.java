package com.yhb.domain.areaProject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 各市项目数量统计信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "V_PROJECT_CITY_PLAN")
@Data
public class VProjectCityPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String cityName; //城市名称

    @Column(length = 20, nullable = false)
    private String districtName; //市县

    private Long projectNum; // 项目数量

    private Long parentId; // 位置id


}
