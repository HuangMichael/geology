package com.yhb.domain.areaProjectCatalog;

/**
 * Created by Administrator on 2017/5/7 0007.
 */
import lombok.Data;
import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
/**
 * 区块项目建设目录信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_AREA_PROJECT_CATALOG")
@Data
public class AreaProjectCatalog implements Serializable {
    @Id
    @SequenceGenerator(name = "AREA_PROJECT_CATALOG_GENERATOR", sequenceName = "SEQ_AREA_PROJECT_CATALOG", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_PROJECT_CATALOG_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String catalogName; //目录名称

    @Column(length = 45, nullable = false)
    private String catalogDesc; //目录描述

    @Column(length = 11)
    private Long parentId; //上级目录

    @Column(length = 11)
    private Long projectId; //所属项目

    @Column(length = 11, nullable = false)
    private Long sortNo; //排序

    @Column(length = 1, nullable = false)
    private String status; //状态
}
