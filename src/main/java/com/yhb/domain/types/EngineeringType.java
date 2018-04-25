package com.yhb.domain.types;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/21.
 */
@Entity
@Table(name = "T_ENGINEERING_TYPE")
@Data
public class EngineeringType implements Serializable {
    @Id
    @SequenceGenerator(name = "ENGINEERING_TYPE_GENERATOR", sequenceName = "SEQ_ENGINEERING_TYPE", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ENGINEERING_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String engineeringType; //工程类型

    @Column(length = 1, nullable = false)
    private String status; //状态
}
