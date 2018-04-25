package com.yhb.domain.location;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 行政区划信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_LOCATION")
@Data
public class Location implements Serializable {
    @Id
    @SequenceGenerator(name = "LOCATION_GENERATOR", sequenceName = "SEQ_LOCATION", allocationSize = 1, initialValue = 30)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String locCode; //位置编码

    @Column(length = 45, nullable = false)
    private String locName; //位置名称

    @Column(length = 11, nullable = false)
    private Long locLevel; //位置级别

    @Column(length = 45)
    private String locDesc; //位置描述

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Location parent; // 上级位置

    @Column(length = 1)
    private String status; //状态
    @Transient
    @Column(name = "objectid")
    private Long objectId;
}

