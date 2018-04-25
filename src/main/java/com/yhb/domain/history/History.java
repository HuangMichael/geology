package com.yhb.domain.history;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.yhb.domain.mediaType.MediaType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 围垦历史沿革信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_AREA_HISTORY")
@Data
public class History implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_HISTORY_GENERATOR", sequenceName = "SEQ_AREA_HISTORY", allocationSize = 1, initialValue = 13)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_HISTORY_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String title; //标题

    @Column(length = 50, nullable = false)
    private String subTitle; //子标题，即在历史沿革描述上面的小标题，可能会与左侧的标题不一样

    @Column(length = 5000)
    private String historyDesc; //历史沿革描述

    //@JsonBackReference
    //为了解决对象中存在双向引用导致的无限递归问题，@JsonBackReference标注的属性在序列化（serialization，即将对象转换为json数据）时，会被忽略（即结果中的json数据不包含该属性的内容）。@JsonManagedReference标注的属性则会被序列化。在序列化时，@JsonBackReference的作用相当于@JsonIgnore，此时可以没有@JsonManagedReference。
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private History parent; // 上级历史沿革

    @Column(nullable = false)
    private Long level; //标题的等级，全国、江苏省为0.其他的记录等级为1

    @Column(nullable = false)
    private Long sortNo; //排序，在父级id相等时，按照sortNo进行排序

    //以下是多媒体资料相关字段
    @Column(length = 50)
    private String fileName; //多媒体文件名称

    @Column()
    private Long fileSize; //多媒体文件大小，单位为B

    @Column(length = 200)
    private String fileRelativeUrl; //多媒体文件相对路径

    @Column(length = 200)
    private String fileAbsoluteUrl; //多媒体文件绝对路径，精确到盘符

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_type_id", referencedColumnName = "id")
    private MediaType mediaType; //媒体资料类型

    @Column(length = 1)
    private String status; //审核状态：1表示已审核，0表示未审核
}

