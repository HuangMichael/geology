package com.yhb.domain.etl;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 * 数据ETL(抽取转换装载)表，元数据表
 */
@Entity
@Table(name = "T_ETL_TABLE")
@Data
public class EtlTable implements Serializable {
    @Id
    @SequenceGenerator(name = "ETL_TABLE_GENERATOR", sequenceName = "SEQ_ETL_TABLE", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ETL_TABLE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 100, nullable = false)
    private String tableDesc; //表的描述

    @Column(length = 100, nullable = false)
    private String domainName; //实体类名称

    @Column(length = 50, nullable = false)
    private String baseTableName; //基础表的名称

    @Column(length = 50, nullable = false)
    private String serviceTableName; //服务表的名称

    @Column(length = 1, nullable = false)
    private String status; //状态

    @Column(length = 1, nullable = true)
    private String dropStatus; //导入数据前是否删除表

}

