package com.yhb.domain.areaProject;

import com.yhb.domain.area.Area;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 项目范围
 */
@Entity
@Table(name = "T_AREA_PROJECT_RANGE")
@Data
public class AreaProjectRange implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_RANGE_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_RANGE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_RANGE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 50, nullable = false)
    private String rangeDesc; //项目范围描述

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area; //所属区块

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private AreaProject project; //所属项目

    @Column(length = 1, nullable = false)
    private String status; //状态
}
