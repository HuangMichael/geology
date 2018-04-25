package com.yhb.domain.areaProject;

/**
 * 区块项目验收信息表
 *
 * @author lulimin
 */

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "V_AREA_PROJECT_ACCEPT")
@Data
public class VAreaProjectAccept implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String projectName;
    //20171101新增四个字段
    private String projectNo; // 所属项目编号
    private String areaName;//所属区块
    private String cityName;//所在市
    private String districtName;//所在区县

    private Double projectSize;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String mainPurpose;
    private String landUsingType;
    private String engineeringType;

    private String budget;
    private String investedSum;
    private String consUnit;
    private String monitorUnit;
    private String acceptUnit;

    private String projectLeader;
    private String leaderContact;

    private String taskProgress;
    private String expert;//专家组长
    private String conclusion;//验收结论
    private String status;

    private String authKey; //数据权限过滤字段
    private String important; //是否为重大项目：1表示重大项目，0表示普通项目
}
