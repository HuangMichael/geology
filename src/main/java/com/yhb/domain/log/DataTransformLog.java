package com.yhb.domain.log;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangbin on 2017/9/8.
 */

@Entity
@Table(name = "T_DATA_TRANSFORM_LOG")
@Data
public class DataTransformLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DATA_TRANSFORM_LOG_GENERATOR", sequenceName = "SEQ_DATA_TRANSFORM_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATA_TRANSFORM_LOG_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20)
    private String userName; //应用名称

    @Column(length = 50)
    private String fileName; //访问地址

    @Column(length = 4)
    private String operateType; //操作类型 导入还是导出


    @Column(length = 20)
    private Date operateTime; //区块编号


}
