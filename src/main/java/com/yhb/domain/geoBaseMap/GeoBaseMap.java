package com.yhb.domain.geoBaseMap;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地图地图图层信息表
 *
 * @author HUANGBIN
 */
@Entity
@Table(name = "T_GEO_BASE_MAP")
@Data
public class GeoBaseMap implements Serializable {
    @Id
    @SequenceGenerator(name = "GEO_BASE_MAP_GENERATOR", sequenceName = "SEQ_GEO_BASE_MAP", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEO_BASE_MAP_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String baseMapName; //图层名称

    @Column(length = 200)
    private String baseMapDesc; //图层描述

    @Column(length = 200, nullable = false)
    private String serviceUrl; //服务地址

    @Column(length = 1)
    private Integer sortNo; //加载顺序

    @Column(length = 1)
    private String status; //状态

}