package com.yhb.domain.areaPlanType;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

/**
 * 区块规划类型信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_AREA_PLAN_TYPE")
@Data
public class AreaPlanType implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PLAN_TYPE_GENERATOR", sequenceName = "SEQ_AREA_PLAN_TYPE", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PLAN_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 45, nullable = false)
    private String typeName; //规划类型名称

    @Column(length = 1, nullable = false)
    private String status; //状态

}

