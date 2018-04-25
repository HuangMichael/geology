package com.yhb.domain.natureReserve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 * 自然保护核心区视图信息
 */
@Entity
@Table(name = "V_NATURE_RESERVE_AREA")
@Data
public class VNatureReserveArea implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号

    private String areaNo; //自然保护区编号

    private String areaName; //自然保护区名称

    private String areaDesc; //自然保护区描述

    private String status; //状态
}
