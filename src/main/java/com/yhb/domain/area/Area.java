package com.yhb.domain.area;

import com.yhb.domain.dataFilter.DataFilter;
import com.yhb.domain.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 区块信息表
 * modified on 2017/10/30 by lulimin
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_AREA")
@Data
public class Area implements Serializable {
    private static final long serialVersionUID = 1L;
    //allocationSize = 1主键增量，每次增加1
    @Id
    @SequenceGenerator(name = "AREA_GENERATOR", sequenceName = "SEQ_AREA", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String areaNo; //区块编号

    @Column(length = 20, nullable = false)
    private String areaName; //区块名称

    @Column(length = 1000)
    private String areaDesc; //区块描述

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private Location city; //所属市

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id", nullable = false)
    private Location district; //所属区县

    //    @Column(nullable = false,columnDefinition = "default 2000")
    private Double avgHeight; //平均起围高程

    @ManyToOne
    @JoinColumn(name = "function_type_id", referencedColumnName = "id", nullable = false)
    private AreaFunctionType functionType; //垦区功能类型

    @ManyToOne
    @JoinColumn(name = "area_type_id", referencedColumnName = "id", nullable = false)
    private AreaType areaType; //区块类型

    @Column(length = 1, nullable = false)
    private String status; //状态

    @Column(length = 1)
    private String inningStatus; //围垦状态：1已围 2在围 3未围

    @Column(length = 100)
    private String locDesc; //区块具体位置的描述

    @Column(length = 100)
    private String mainPurpose; //区块主要用途

    @Column(scale = 5, precision = 3)
    private Double buildSize; //总的规划面积

    @Column(scale = 5, precision = 3)
    private Double inningSize; //总的已围面积

    @Column(length = 10, nullable = false, updatable = false)
    private String authKey; //数据权限过滤字段
}
