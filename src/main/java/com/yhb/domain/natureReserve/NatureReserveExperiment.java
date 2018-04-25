package com.yhb.domain.natureReserve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 自然保护试验区信息表
 *
 * @author lijina  on 2017/6/22.
 */
@Entity
@Table(name = "T_NATURE_RESERVE_EXPERIMENT")
@Data
public class NatureReserveExperiment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "NATURE_RESERVE_EXPERIMENT_GENERATOR", sequenceName = "SEQ_NATURE_RESERVE_EXPERIMENT", allocationSize = 1, initialValue = 2)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NATURE_RESERVE_EXPERIMENT_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String experimentNo; //自然保护试验区编号

    @Column(length = 20, nullable = false)
    private String experimentName; //自然保护试验区名称

    @Column(length = 1000)
    private String experimentDesc; //自然保护试验区描述

    @Column(length = 1, nullable = false)
    private String status; //状态
}
