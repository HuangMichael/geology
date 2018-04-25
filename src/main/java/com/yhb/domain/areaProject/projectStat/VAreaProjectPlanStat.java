package com.yhb.domain.areaProject.projectStat;


import lombok.Data;

import javax.persistence.*;

/**
 * 项目信息统计
 *
 * @author huangbin
 */
@Entity
@Table(name = "v_area_project_plan_stat")
@Data

public class VAreaProjectPlanStat {


    public VAreaProjectPlanStat() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String prov; //序号
    private String city; //位置名称
    private String district; //序号
    private String engineeringType; //位置名称
    private Integer projectNum; //项目数量
}
