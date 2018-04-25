package com.yhb.domain.area;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 区块信息视图
 *
 * @author lulimin
 */
@Entity
@Table(name = "V_AREA")
@Data
public class VArea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String areaNo; //区块编号
    private String areaName; //区块名称
    private String areaDesc; //区块描述
    private String cityName; //城市名称
    private String districtName; //县区名称
    private Double avgHeight; //平均起围高程(米)
    private String areaType; //区块类型
    private String functionType; //功能类型

    private String status; //状态
    private String authKey; //数据权限过滤字段
    private String inningStatus; //围垦状态：1已围 2在围 3未围

    private String locDesc; //区块具体位置的描述
    private String mainPurpose; //区块主要用途
    private Double buildSize; //总的规划面积
    private Double inningSize; //总的已围面积
}
