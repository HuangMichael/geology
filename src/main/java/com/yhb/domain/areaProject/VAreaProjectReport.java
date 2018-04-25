package com.yhb.domain.areaProject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/26.
 */
@Entity
@Table(name = "V_AREA_PROJECT_REPORT")
@Data
public class VAreaProjectReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String projectName; // 所属项目名称
    //20171031新增四个字段
    private String projectNo; // 所属项目编号
    private String areaName;//所属区块
    private String cityName;//所在市
    private String districtName;//所在区县

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginDate; //实施开始时间，年月

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate; //实施结束时间，年月

    private Double projectSize; //匡围面积
    private String dykeLevel; //围堤等级
    private String designStandard; //设计标准
    private String dykeLineSetting; //堤线布置
    private String dykeSectionalType;//堤身断面类型
    private String dykeTopHeight;//堤顶高程
    private String dykeTopWidth;//堤顶宽度
    private String slopeRatio; //坡比
    private Double investedSum;//投资额（亿元）
    private Double investedProvince;//其中省级补助资金（亿元）
    private Double investedCity;//市县补助资金（亿元）
    private Double investedSelf;//自筹资金（亿元）
    private String status;//状态
    private String authKey; //数据权限过滤字段
    private String important; //是否为重大项目，分为两类：重大项目、普通项目
}
