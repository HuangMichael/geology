package com.yhb.domain.etl;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/16.
 * 数据ETL(抽取转换装载)配置表
 */
@Entity
@Table(name = "T_ETL_TABLE_CONFIG")
@Data
public class EtlTableConfig implements Serializable {
    @Id
    @SequenceGenerator(name = "ETL_TABLE_CONFIG_GENERATOR", sequenceName = "SEQ_ETL_TABLE_CONFIG", allocationSize = 1, initialValue = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ETL_TABLE_CONFIG_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号
//
//    @Column(length = 50)
//    private String entityColName; //实体类字段名

    @Column(length = 50, nullable = false)
    private String baseColName; //基础表数据库字段名

    @Column(length = 100, nullable = false)
    private String baseColDesc; //基础表列的描述


    @Column(length = 50, nullable = false)
    private String serviceColName; //数据库字段名

    @Column(length = 50, nullable = false)
    private String dataType; //该列的数据类型


    @Column(length = 100, nullable = false)
    private String classType; //对应的类的类型

    @Column()
    private Long length; //数据长度如果有的话

    @ManyToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id", nullable = false)
    private EtlTable table; //所对应的EtlTable


    @Column(length = 1)
    private String referenceType; // 0不参考  1 参考 2 参考静态值列表

    @Column(length = 20)
    private String referenceTable; //参考表名


    @Column(length = 20)
    private String referenceColName; //参考字段名

    @Column(length = 2)
    private Long sortNo; //排序

    @Column(length = 1, nullable = false)
    private String status; //状态

    @Column(length = 1, nullable = false)
    private String isNull;//表示该列属性是否为空
}
