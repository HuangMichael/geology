package com.yhb.domain.area;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 区块功能类型信息表
 *
 * @author huangbin
 * @Date 2017-5-11 11:28:55
 */
@Entity
@Table(name = "T_AREA_FUNCTION_TYPE")
@Data
public class AreaFunctionType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "AREA_FUNCTION_TYPE_GENERATOR", sequenceName = "SEQ_AREA_FUNCTION_TYPE", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_FUNCTION_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String functionType; //区块功能类型名称

    @Column(length = 1, nullable = false)
    private String status; //状态
}
