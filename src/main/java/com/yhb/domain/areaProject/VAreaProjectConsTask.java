package com.yhb.domain.areaProject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yhb.domain.area.Area;
import com.yhb.domain.location.Location;
import com.yhb.utils.DateUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
@Entity
@Table(name = "V_AREA_PROJECT_CONS_TASK")
@Data
public class VAreaProjectConsTask implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
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
    private String engineeringType; //工程类型
    private String consTask; //建设任务
    private String status;//状态
    private String authKey; //数据权限过滤字段
    private String important; //是否为重大项目：1表示重大项目，0表示普通项目
}
