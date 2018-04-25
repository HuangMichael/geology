package com.yhb.domain.areaBuilding;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/9/26.
 * 查询统计-统计分析报表-围垦区块统计分析
 */
@Entity
@Table(name = "V_AREA_STATISTICS")
@Data
public class VAreaStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private Long areaId; //序号
    private String areaNo; //围垦区块编号
    private Double buildSize; //总用地围垦面积
    private Double nyBuildSize;//农业用地面积
    private Double jsBuildSize;//建设用地面积
    private Double stBuildSize;//生态用地面积
}
