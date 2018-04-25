package com.yhb.domain.areaProject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangbin on 2017/5/25 0025.
 * 项目可行性研究报告
 */
@Entity
@Table(name = "T_AREA_PROJECT_REPORT")
@Data
public class AreaProjectReport implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_REPORT_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_REPORT", allocationSize = 1, initialValue = 8)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_REPORT_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @DateTimeFormat(pattern = "yyyy-MM")
    @Temporal(TemporalType.DATE)
    private Date beginDate; //实施开始时间，年月

    @DateTimeFormat(pattern = "yyyy-MM")
    @Temporal(TemporalType.DATE)
    private Date endDate; //实施结束时间，年月

//    private Double projectSize; //匡围面积

    @Column(length = 20)
    private String dykeLevel; //围堤等级

    @Column(length = 100)
    private String designStandard; //设计标准

    @Column(length = 1000)
    private String dykeLineSetting; //堤线布置

    @Column(length = 100)
    private String dykeSectionalType;//堤身断面类型

    @Column(length = 100)
    private String dykeTopHeight;//堤顶高程

    @Column(length = 100)
    private String dykeTopWidth;//堤顶宽度

    @Column(length = 20)
    private String slopeRatio; //坡比

    private Double investedSum;//投资额（亿元）


    private Double investedProvince;//其中省级补助资金（亿元）


    private Double investedCity;//市县补助资金（亿元）


    private Double investedSelf;//自筹资金（亿元）

    @Column(length = 1, nullable = false)
    private String status;//状态

    @OneToOne
    @JoinColumn(name = "project_id", foreignKey = @ForeignKey(name = "fk_project_id"), nullable = false)
    private AreaProject project; // 所属项目

//    @Column(length = 10, nullable = false, updatable = false)
//    private String authKey; //数据权限过滤字段，授权码使用相关联的项目的授权码
}
