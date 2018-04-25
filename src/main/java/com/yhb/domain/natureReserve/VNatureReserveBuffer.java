package com.yhb.domain.natureReserve;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 * 自然保护缓冲区视图信息
 */
@Entity
@Table(name = "V_NATURE_RESERVE_BUFFER")
@Data
public class VNatureReserveBuffer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号

    private String bufferNo; //自然保护缓冲区编号

    private String bufferName; //自然保护缓冲区名称

    private String bufferDesc; //自然保护缓冲区描述

    private String status; //状态
}
