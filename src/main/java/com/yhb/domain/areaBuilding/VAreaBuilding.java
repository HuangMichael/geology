package com.yhb.domain.areaBuilding;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 区块围垦信息视图
 *
 * @author HUANGBIN
 */
@Entity
@Table(name = "V_AREA_BUILDING")
@Data
public class VAreaBuilding implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String areaNo; //围垦区块编号
    private String areaName;//围垦区块名称
    private String buildDesc;//围垦区块描述

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginYear;//开始年份

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endYear;//结束年份
    private Double buildSize; //总用地围垦面积
    private Double nyBuildSize;//农业用地面积
    private Double jsBuildSize;//建设用地面积
    private Double stBuildSize;//生态用地面积
    private String cityName ;//城市名称
    private String districtName ;//区县名称
    private String manager; //围垦区块负责人
    private String memo; //备注信息
    private String status; //状态
    private String authKey; //数据权限过滤字段
}
