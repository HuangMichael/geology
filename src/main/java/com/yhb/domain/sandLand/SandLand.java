package com.yhb.domain.sandLand;

import com.yhb.domain.location.Location;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by llm on 2017/7/31.
 */
@Entity
@Table(name = "T_SAND_LAND")
@Data
public class SandLand {
    @Id
    @SequenceGenerator(name = "SAND_LAND_GENERATOR", sequenceName = "SEQ_SAND_LAND", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAND_LAND_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, nullable = false)
    private String sandNo; //沙洲编号，此处起名为故意不遵循驼峰命名法。为了在地图创建界面使用公共方法。

    @Column(length = 50, nullable = false)
    private String sandName; //沙洲名称，此处起名为故意不遵循驼峰命名法。为了在地图创建界面使用公共方法。

    @Column(length = 50)
    private String position;//所在位置，文字描述

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //所属行政区划

    private Double length; //南北长

    private Double width; //东西宽

    @Column(length = 1, nullable = false)
    private String status; //状态
}
