package com.yhb.domain.area;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 区块类型信息表
 *
 * @author huangbin
 * @Date 2017-5-11 11:28:55
 */
@Entity
@Table(name = "T_AREA_TYPE")
@Data
public class AreaType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AREA_TYPE_GENERATOR", sequenceName = "SEQ_AREA_TYPE", allocationSize = 100, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String type; //区块类型名称

    @Column(length = 1, nullable = false)
    private String status; //状态


}
