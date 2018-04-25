package com.yhb.domain.beach;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 潮位类型信息表
 *
 * Created by Administrator on 2017/7/31.
 */
@Entity
@Table(name = "T_SEA_LEVEL_TYPE")
@Data
public class SeaLevelType implements Serializable {
    @Id
    @SequenceGenerator(name = "SEA_LEVEL_TYPE_GENERATOR", sequenceName = "SEQ_SEA_LEVEL_TYPE", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEA_LEVEL_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 50, nullable = false)
    private String typeName; //潮位类型名称，1.潮下带；2.低潮滩；3.中潮滩；4.高潮滩

    @Column(length = 1, nullable = false)
    private String status; //状态
}
