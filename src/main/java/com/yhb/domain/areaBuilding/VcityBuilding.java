package com.yhb.domain.areaBuilding;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 区块围垦信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "V_CITY_BUILDING")
@Data
public class VcityBuilding implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String locName; //城市名称

    @Column(scale = 5, precision = 3)
    private Double buildSize; // 建设总面积

    @Column(scale = 5, precision = 3)
    private Double nyBuildSize; //农业用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double jsBuildSize; //建设用地围垦面积

    @Column(scale = 5, precision = 3)
    private Double stBuildSize; //生态用地围垦面积

    private Long parentId; //上级位置id
}
