package com.yhb.domain.natureReserve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 * 自然保护试验区视图信息
 */
@Entity
@Table(name = "V_NATURE_RESERVE_EXPERIMENT")
@Data
public class VNatureReserveExperiment  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号

    private String experimentNo; //自然保护试验区编号

    private String experimentName; //自然保护试验区名称

    private String experimentDesc; //自然保护试验区描述

    private String status; //状态
}
