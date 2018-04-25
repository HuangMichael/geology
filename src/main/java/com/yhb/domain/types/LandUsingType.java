package com.yhb.domain.types;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by Administrator on 2017/7/21.
 */
@Entity
@Table(name = "T_LAND_USING_TYPE")
@Data
public class LandUsingType implements Serializable {
    @Id
    @SequenceGenerator(name = "LAND_USING_TYPE_GENERATOR", sequenceName = "SEQ_LAND_USING_TYPE", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LAND_USING_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号
    
    @Column(length = 20, nullable = false)
    private String landUsingType; //用地类型

    @Column(length = 1, nullable = false)
    private String status; //状态
}
