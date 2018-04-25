package com.yhb.domain.geoLegend;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
/**
 * 地图用例信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_GEO_LEGEND")
@Data
public class GeoLegend implements Serializable {
    @Id
    @SequenceGenerator(name = "GEO_LEGEND_GENERATOR", sequenceName = "SEQ_GEO_LEGEND", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEO_LEGEND_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String legendName; //图例名称

    @Column(length = 45, nullable = false)
    private String legendUrl; //图例路径


    @Column(length = 100)
    private String legendDesc; //图例描述

    @Column(length = 1)
    private String status; //状态

}