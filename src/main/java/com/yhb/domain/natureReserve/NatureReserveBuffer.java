package com.yhb.domain.natureReserve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 自然保护区缓冲区信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_NATURE_RESERVE_BUFFER")
@Data
public class NatureReserveBuffer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "NATURE_RESERVE_BUFFER_GENERATOR", sequenceName = "SEQ_NATURE_RESERVE_BUFFER", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NATURE_RESERVE_BUFFER_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String bufferNo; //自然保护区编号

    @Column(length = 20, nullable = false)
    private String bufferName; //自然保护区名称

    @Column(length = 1000)
    private String bufferDesc; //自然保护区描述

    @Column(length = 1, nullable = false)
    private String status; //状态


}
