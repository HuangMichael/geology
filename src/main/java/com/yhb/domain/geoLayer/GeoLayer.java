package com.yhb.domain.geoLayer;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地图图层信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_GEO_LAYER")
@Data
public class GeoLayer implements Serializable {
    @Id
    @SequenceGenerator(name = "GEO_LAYER_GENERATOR", sequenceName = "SEQ_GEO_LAYER", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEO_LAYER_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 45, nullable = false)
    private String layerName; //图层名称

    @Column(length = 100)
    private String layerDesc; //图层描述

    @Column(length = 200, nullable = false)
    private String serviceUrl; //服务地址

    @Column(length = 10)
    private String fillColor; //填充色

    @Column(length = 1)
    private String theme; //所属主题

    @Column(length = 1)
    private String status; //状态

}