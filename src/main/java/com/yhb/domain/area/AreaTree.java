package com.yhb.domain.area;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/6.
 */
@Entity
@Table(name = "V_AREA_TREE")
@Data
public class AreaTree implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //序号
    private Long pId; //父节点序号
    private String name; //区块名称
    private String t;   //区块描述
    private String authKey;//授权码
    private String status;//审核状态
}
