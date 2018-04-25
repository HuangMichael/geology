package com.yhb.domain.history;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12.
 * 历史沿革信息视图
 */
@Entity
@Table(name = "V_HISTORY")
@Data
public class VHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String title; //标题
    private Long sortNo; //排序
    private String status; //状态
    private Long parentId; //上级节点的id
    private Long level; //级别
}
