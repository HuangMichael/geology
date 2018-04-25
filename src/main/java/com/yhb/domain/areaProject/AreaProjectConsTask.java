package com.yhb.domain.areaProject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 项目建设任务信息表
 */
@Entity
@Table(name = "T_AREA_PROJECT_CONS_TASK")
@Data
public class AreaProjectConsTask implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_CONS_TASK_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_CONS_TASK", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_CONS_TASK_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private AreaProject project;

    @DateTimeFormat(pattern = "yyyy-MM")
    @Temporal(TemporalType.DATE)
    private Date beginDate; //实施开始时间，年月

    @DateTimeFormat(pattern = "yyyy-MM")
    @Temporal(TemporalType.DATE)
    private Date endDate; //实施结束时间，年月

//    @Column(length = 50)
//    private String mainPurpose; //主要用途 x
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "engineering_type_id", referencedColumnName = "id")
//    private EngineeringType engineeringType; //工程类型 x

    @Column(length = 1000)
    private String consTask; //建设任务

    @Column(length = 1, nullable = false)
    private String status;//状态

//    @Column(length = 10, nullable = false, updatable = false)
//    private String authKey; //数据权限过滤字段
}
