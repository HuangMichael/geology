package com.yhb.domain.areaPlan;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 区块总体规划
 *
 * @author huangbin
 */
@Entity
@Table(name = "V_TOTAL_PLAN_AREA")
@Data
public class VTotalPlanArea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String areaNo; //城市名称

    @Column(length = 20, nullable = false)
    private String areaName; //城市名称

    @Column(scale = 5, precision = 3)
    private Double buildSize; // 建设总面积

    @Column(scale = 5, precision = 3)
    private Double nyBuildSize; //农业用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double jsBuildSize; //建设用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double stBuildSize; //生态用地围垦面积


}
