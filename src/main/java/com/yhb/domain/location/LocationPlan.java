package com.yhb.domain.location;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 位置围垦规划描述信息
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_LOCATION_PLAN")
@Data
public class LocationPlan implements Serializable {
    @Id
    @SequenceGenerator(name = "LOCATION_PLAN_GENERATOR", sequenceName = "SEQ_LOCATION_PLAN", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_PLAN_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location; // 位置信息

    @Column(length = 1000, nullable = false)
    private String description; //围垦规划描述

    @Column(length = 1)
    private String status; //状态
}

