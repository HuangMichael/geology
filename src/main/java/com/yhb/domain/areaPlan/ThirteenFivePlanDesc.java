package com.yhb.domain.areaPlan;


import com.yhb.domain.dataFilter.DataFilter;
import com.yhb.domain.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 区块项目规划描述信息表
 */
@Entity
@Table(name = "T_THIRTEEN_FIVE_PLAN_DESC")
@Data
public class ThirteenFivePlanDesc extends DataFilter implements Serializable {
    @Id
    @SequenceGenerator(name = "THIRTEEN_FIVE_PLAN_DESC_GENERATOR", sequenceName = "SEQ_THIRTEEN_FIVE_PLAN_PLAN_DESC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THIRTEEN_FIVE_PLAN_DESC_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //所属行政区划

    @Column(length = 500, nullable = false)
    private String locationDesc; //数据权限过滤字段


//    @Column(length = 10, nullable = false, columnDefinition = "varchar default 01")
//    private String authKey; //数据权限过滤字段

    @Column(length = 1, nullable = false, columnDefinition = "varchar default 1")
    private String status; //状态
}
