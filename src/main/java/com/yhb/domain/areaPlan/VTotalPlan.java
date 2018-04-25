package com.yhb.domain.areaPlan;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/17.
 * 总体规划信息视图
 */
@Entity
@Table(name = "V_TOTAL_PLAN")
@Data
public class VTotalPlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String areaNo;//所属区块编号
    private String areaName;//所属区块
    private String planDesc;//区块规划描述
    private String cityName;//所在市
    private String districtName;//所在区县

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginYear;//开始年份

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endYear;//结束年份

    private Double buildSize; //总围垦面积
    private Double nyBuildSize; //农业用地围垦面积
    private Double jsBuildSize; //建设用地围垦面积
    private Double stBuildSize; //生态用地围垦面积
    private Double reportSize;//上报计划围垦面积
    private String memo;//备注信息

    private String authKey; //数据权限过滤字段
    private String status; //状态
}
