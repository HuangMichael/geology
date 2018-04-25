package com.yhb.domain.areaProject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/31.
 */
@Entity
@Table(name = "V_AREA_PROJECT")
@Data
public class VAreaProject implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id; //序号
    private String projectNo; //项目编号
    private String projectName; //项目名称
    private String areaName; //所属区块
    private String cityName;//所在市
    private String districtName;//所在区县

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginYear; //开始年份

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endYear; //结束年份
    @Column(scale = 5, precision = 3)
    private Double projectSize; //项目占地面积
    private String mainPurpose; //主要用途
    private String projectLeader; //项目负责人
    private String leaderContact; //项目负责人电话
    private String consUnit; //施工单位
    private String monitorUnit; //监管单位
    private String acceptUnit; //验收单位
    private Double budget; //预算金额
    private Double investedSum; //已投资金额
    private String taskProgress; //投资进度
    private String landUsingType; //用地类型
    private String engineeringType; //工程类型
    private String authKey; //数据权限过滤字段
    private String status; //状态
    private String buildStatus; //项目建设状态   0为规划  1 为在建
    private String important; //是否为重大项目：1表示重大项目，0表示普通项目
}
