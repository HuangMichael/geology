package com.yhb.domain.areaBuilding;

import com.yhb.domain.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 围垦历史演变信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_HISTORY_BUILDING")
@Data
public class HistoryBuilding implements Serializable {
    @Id
    @SequenceGenerator(name = "HISTORY_BUILDING_GENERATOR", sequenceName = "SEQ_HISTORY_BUILDING", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORY_BUILDING_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10)
    private String period; //状态


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //所在市

    private Double buildSize; //围垦面积

    @Column(length = 2)
    private Long sortNo; //排序号

    @Column(length = 100)
    private String buildDesc; //状态


    @Column(length = 1)
    private String status; //状态
}
