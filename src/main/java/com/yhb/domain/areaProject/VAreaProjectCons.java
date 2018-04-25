package com.yhb.domain.areaProject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/26.
 * 项目建设基本信息视图
 */
@Entity
@Table(name = "V_AREA_PROJECT_CONS")
@Data
public class VAreaProjectCons {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    private String projectName;//项目名称
    //20171101新增四个字段
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

    private String mainPurpose; //主要用途
    private String projectLeader; //项目负责人
    private String leaderContact; //项目负责人电话
    private String consUnit; //施工单位
    private String monitorUnit; //监管单位
    private String acceptUnit; //验收单位
    private String status;//状态
    private String authKey; //数据权限过滤字段
    private String important; //是否为重大项目，分为两类：重大项目、普通项目
}
