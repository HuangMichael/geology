package com.yhb.domain.areaProject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 全省项目数量统计信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "V_PROJECT_PROV_PLAN")
@Data
public class VProjectProvPlan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String cityName; //城市名称

    private Long projectNum; // 项目数量


}
