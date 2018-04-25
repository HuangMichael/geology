package com.yhb.domain.areaProject;

/**
 * Created by llm on 2017/5/7 0003.
 * <p>
 * 区块项目验收信息表
 *
 * @author lulimin
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "T_AREA_PROJECT_ACCEPT")
@Data
public class AreaProjectAccept implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_ACCEPT_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_ACCEPT", allocationSize = 1, initialValue = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_ACCEPT_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private AreaProject project;

    @Column(length = 10)
    private String expert; //专家组长

    @Column(length = 1000)
    private String conclusion; //验收结论

    @Column(length = 1, nullable = false)
    private String status; //状态

//    @DateTimeFormat(pattern = "yyyy-MM")
//    @Temporal(TemporalType.DATE)
//    private Date beginDate; //实施开始时间，年月
//
//    @DateTimeFormat(pattern = "yyyy-MM")
//    @Temporal(TemporalType.DATE)
//    private Date endDate; //实施结束时间，年月
//
//    @Column(length = 50)
//    private String mainPurpose; //主要用途x
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "land_using_type_id", referencedColumnName = "id")
//    private LandUsingType landUsingType; //用地类型x
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "engineering_type_id", referencedColumnName = "id")
//    private EngineeringType engineeringType; //工程类型x
//
//    private Double budget; //预算金额
//
//    private Double investedSum; //已投资金额
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
//    @Column(length = 20)
//    private String projectLeader; //项目负责人
//
//    @Column(length = 12)
//    private String leaderContact; //项目负责人电话
//
//    @Column(length = 45)
//    private String taskProgress; //投资进度


//    @Column(length = 10, nullable = false, updatable = false)
//    private String authKey; //数据权限过滤字段
}
