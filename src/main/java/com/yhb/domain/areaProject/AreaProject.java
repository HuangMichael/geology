package com.yhb.domain.areaProject;

/**
 * Created by llm on 2017/5/7 0003.
 * <p>
 * 区块项目信息表
 */

import com.yhb.domain.area.Area;
import com.yhb.domain.location.Location;
import com.yhb.domain.types.EngineeringType;
import com.yhb.domain.types.LandUsingType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "T_AREA_PROJECT")
@Data
public class AreaProject implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_GENERATOR", sequenceName = "SEQ_AREA_PROJECT", allocationSize = 1, initialValue = 14)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, nullable = false)
    private String projectNo; //项目编号

    @Column(length = 50, nullable = false)
    private String projectName; //项目名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area; //所属区块，可以为空，因为道路工程和水利工程并没有对应的区块

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private Location city; //所属市

    @ManyToOne
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private Location district; //所属区县

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date beginYear; //开始年份

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endYear; //结束年份

    @Column(scale = 5, precision = 3)
    private Double projectSize; //项目占地面积

    @Column(length = 200)
    private String mainPurpose; //主要用途

    @Column(length = 20)
    private String projectLeader; //项目负责人

    @Column(length = 12)
    private String leaderContact; //项目负责人电话

    @Column(length = 45)
    private String consUnit; //施工单位

    @Column(length = 45)
    private String monitorUnit; //监管单位

    @Column(length = 45)
    private String acceptUnit; //验收单位

    private Double budget; //预算金额

    private Double investedSum; //已投资金额

    @Column(length = 45)
    private String taskProgress; //投资进度

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_using_type_id", referencedColumnName = "id")
    private LandUsingType landUsingType; //用地类型

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engineering_type_id", referencedColumnName = "id")
    private EngineeringType engineeringType; //工程类型

    @Column(length = 1, nullable = false)
    private String status; //状态

    @Column(length = 1)
    private String buildStatus; //项目建设状态   0为规划  1 为在建  2 为已建

    @Column(length = 10, nullable = false, updatable = false)
    private String authKey; //数据权限过滤字段

    @Column(length = 1, nullable = false)
    private String important; //是否为重大项目：1表示重大项目，0表示普通项目
}
