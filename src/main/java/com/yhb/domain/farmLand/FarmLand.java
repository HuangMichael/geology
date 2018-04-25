package com.yhb.domain.farmLand;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 耕地信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_FARM_LAND")
@Data
public class FarmLand implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "FARM_LAND_GENERATOR", sequenceName = "SEQ_FARM_LAND", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FARM_LAND_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String landNo; //编号

    @Column(length = 50, nullable = false)
    private String landName; //名称

    private Double landSize; //耕地面积

    @Column(length = 10)
    private String landType ;  //土壤类型

    @Column(length = 1, nullable = false)
    private String status; //状态


}
