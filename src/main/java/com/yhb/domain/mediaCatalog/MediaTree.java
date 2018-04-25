package com.yhb.domain.mediaCatalog;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 多媒体库树信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_MEDIA_TREE")
@Data
public class MediaTree implements Serializable {
    @Id
    @SequenceGenerator(name = "MEDIA_TREE_GENERATOR", sequenceName = "SEQ_MEDIA_TREE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIA_TREE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号


    @Column(length = 50, nullable = false)
    private String treeNodeCode; //多媒体树节点编码


    @Column(length = 20, nullable = false)
    private String treeName; //多媒体树名称

    @Column(length = 20, nullable = false)
    private String treeDesc; //多媒体树节点描述

    @Column(length = 1, nullable = false)
    private String treeType; //多媒体类型

    @Column(length = 10)
    private String authKey; //备注信息

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private MediaTree parent; // 上级位置


    @Column(length = 1)
    private String status; //备注信息

}


