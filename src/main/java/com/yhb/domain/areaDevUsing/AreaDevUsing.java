package com.yhb.domain.areaDevUsing;

import com.yhb.domain.mediaType.MediaType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
/**
 * Created by 路丽民 on 2017/5/5 0005.
 */


/**
 * 区块开发利用信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_AREA_DEV_USING")
@Data
public class AreaDevUsing implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_DEV_USING_GENERATOR", sequenceName = "SEQ_AREA_DEV_USING", allocationSize = 1, initialValue = 15)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_DEV_USING_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String title; //标题

    @Column(length = 50,nullable = false)
    private String subTitle; //子标题，即在开发利用描述上面的小标题，可能会与左侧的标题不一样

    @Column(length = 5000)
    private String devUsingDesc; //开发利用描述

    //@JsonBackReference
//为了解决对象中存在双向引用导致的无限递归问题，@JsonBackReference标注的属性在序列化（serialization，即将对象转换为json数据）时，会被忽略（即结果中的json数据不包含该属性的内容）。@JsonManagedReference标注的属性则会被序列化。在序列化时，@JsonBackReference的作用相当于@JsonIgnore，此时可以没有@JsonManagedReference。
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private AreaDevUsing parent; // 上级开发利用

    @Column(nullable = false)
    private Long level; //标题的等级，港口建设、产业升级、生态建设、公共基础建设等记录为0.其他的记录等级为1

    @Column(nullable = false)
    private Long sortNo; //排序，在父级id相等时，按照sortNo进行排序


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
    private String status; //状态

}