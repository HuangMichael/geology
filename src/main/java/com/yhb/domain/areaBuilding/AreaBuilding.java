package com.yhb.domain.areaBuilding;

import com.yhb.domain.area.Area;
import com.yhb.domain.dataFilter.DataFilter;
import com.yhb.domain.location.Location;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 区块围垦进度信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_AREA_BUILDING")
@Data
public class AreaBuilding extends DataFilter implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_BUILDING_GENERATOR", sequenceName = "SEQ_AREA_BUILDING", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_BUILDING_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id", nullable = false)
    private Area area; //所属区块

    @Column(length = 1000, nullable = false)
    private String buildDesc; //区块围垦描述

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginYear; //开始年份

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endYear; //结束年份

    @Column(scale = 5, precision = 3)
    private Double buildSize; //总用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double nyBuildSize; //农业用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double jsBuildSize; //建设用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double stBuildSize; //生态用地围垦面积

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private Location city; //市

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private Location district; //区县

    @Column(length = 45)
    private String manager; //区块负责人

    @Column(length = 1000)
    private String memo; //备注信息

    @Column(length = 1)
    private String status; //状态

//    @Column(length = 10, nullable = false)
//    private String authKey; //数据权限过滤字段
}
