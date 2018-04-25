package com.yhb.domain.areaProject;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 项目范围
 */
@Entity
@Table(name = "T_AREA_PROJECT_BUILDING")
@Data
public class AreaProjectBuilding implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_BUILDING_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_BUILDING", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_BUILDING_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 100, nullable = false)
    private String building_no; //项目范围描述

    @Column(length = 100, nullable = false)
    private String building_name; //项目范围描述

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private AreaProject project; //所属项目

    @Column(length = 1, nullable = false)
    private String status; //状态
}
