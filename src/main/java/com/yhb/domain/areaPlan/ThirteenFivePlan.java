package com.yhb.domain.areaPlan;

import com.yhb.domain.area.Area;
import com.yhb.domain.areaPlanType.AreaPlanType;
import com.yhb.domain.dataFilter.DataFilter;
import com.yhb.domain.location.Location;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/14.
 * 十三五规划信息表
 */
@Entity
@Table(name = "T_THIRTEEN_FIVE_PLAN")
@Data
public class ThirteenFivePlan extends DataFilter implements Serializable {
    @Id
    @SequenceGenerator(name = "THIRTEEN_FIVE_PLAN_GENERATOR", sequenceName = "SEQ_THIRTEEN_FIVE_PLAN", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THIRTEEN_FIVE_PLAN_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id", nullable = false)
    private Area area; //所属区块

    @Column(length = 1000, nullable = false)
    private String planDesc; //区块规划描述

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private Location city; //所在市

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private Location district; //所在区县

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginYear; //开始年份

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endYear; //结束年份

    @Column(scale = 5, precision = 3)
    private Double buildSize; //总规划面积，三种用地类型面积之和

    @Column(scale = 5, precision = 3, nullable = false)
    private Double nyBuildSize; //农业用地围垦面积

    @Column(scale = 5, precision = 3, nullable = false)
    private Double jsBuildSize; //建设用地围垦面积

    @Column(scale = 5, precision = 3, nullable = false)
    private Double stBuildSize; //生态用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double reportSize; //上报计划围垦面积

    @Column(length = 1000)
    private String memo; //备注信息

//    @Column(length = 10, nullable = false)
//    private String authKey; //数据权限过滤字段

    @Column(length = 1, nullable = false)
    private String status; //状态

}