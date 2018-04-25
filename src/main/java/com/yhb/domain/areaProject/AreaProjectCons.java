package com.yhb.domain.areaProject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21 0021.
 * 项目建设基本信息表
 */
@Entity
@Table(name = "T_AREA_PROJECT_CONS")
@Data
public class AreaProjectCons implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_CONS_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_CONS", allocationSize = 1, initialValue = 15)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_CONS_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private AreaProject project;//项目

//    @DateTimeFormat(pattern = "yyyy-MM")
//    @Temporal(TemporalType.DATE)
//    private Date beginDate; //实施开始时间，年月
//
//    @DateTimeFormat(pattern = "yyyy-MM")
//    @Temporal(TemporalType.DATE)
//    private Date endDate; //实施结束时间，年月
//
//    @Column(length = 50)
//    private String mainPurpose; //主要用途
//
//    @Column(length = 20)
//    private String projectLeader; //项目负责人
//
//    @Column(length = 12)
//    private String leaderContact; //项目负责人电话
//
//    @Column(length = 45)
//    private String consUnit; //施工单位
//
//    @Column(length = 45)
//    private String monitorUnit; //监管单位
//
//    @Column(length = 45)
//    private String acceptUnit; //验收单位
//
    @Column(length = 1, nullable = false)
    private String status;//状态

//    @Column(length = 10, nullable = false, updatable = false)
//    private String authKey; //数据权限过滤字段，授权码使用相关联的项目的授权码
}
