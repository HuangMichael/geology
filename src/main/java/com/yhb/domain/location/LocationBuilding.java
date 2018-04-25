package com.yhb.domain.location;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yhb.domain.dataFilter.DataFilter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 位置围垦现状描述信息
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_LOCATION_BUILDING")
@Data
public class LocationBuilding extends DataFilter implements Serializable {
    @Id
    @SequenceGenerator(name = "LOCATION_BUILDING_GENERATOR", sequenceName = "SEQ_LOCATION_BUILDING", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_BUILDING_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

//@JsonBackReference标注的属性在序列化（serialization，即将对象转换为json数据）时，会被忽略（即结果中的json数据不包含该属性的内容）。
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location; // 位置信息

    @Column(length = 1000, nullable = false)
    private String description; //围垦描述

    @Column(length = 1)
    private String status; //状态
}

