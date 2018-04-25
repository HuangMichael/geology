package com.yhb.domain.etl;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/16.
 */
@Entity
@Table(name = "V_ETL_TABLE_TREE")
@Data
public class EtlTableTree implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //序号
    private Long pId; //父节点序号
    private String name; //etl表的名称
    private String t;   //etl表的描述
}
