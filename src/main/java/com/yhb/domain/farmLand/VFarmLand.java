package com.yhb.domain.farmLand;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/13.
 */
@Entity
@Table(name = "V_FARM_LAND")
@Data
public class VFarmLand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号
    private String landNo; //沙洲编号
    private String landName; //名称
    private Double landSize; //耕地面积
    private String landType ;  //土壤类型
    private String status; //状态
}
