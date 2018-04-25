package com.yhb.domain.natureReserve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 自然保护核心区信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_NATURE_RESERVE_AREA")
@Data
public class NatureReserveArea implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "NATURE_RESERVE_AREA_GENERATOR", sequenceName = "SEQ_NATURE_RESERVE_AREA", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NATURE_RESERVE_AREA_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String areaNo; //自然保护区编号

    @Column(length = 20, nullable = false)
    private String areaName; //自然保护区名称

    @Column(length = 1000)
    private String areaDesc; //自然保护区描述

    @Column(length = 1, nullable = false)
    private String status; //状态


}
