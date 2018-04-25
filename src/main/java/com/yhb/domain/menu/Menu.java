package com.yhb.domain.menu;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_MENU")
@Data
public class Menu implements Serializable {
    @Id
    @SequenceGenerator(name = "MENU_GENERATOR", sequenceName = "SEQ_MENU", allocationSize = 1, initialValue = 200)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENU_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, nullable = false)
    private String menuNo; //菜单编号

    @Column(length = 20, nullable = false)
    private String menuName; //菜单名称


    @Column(length = 20)
    private String appName; //应用名称


    @Column(length = 100)
    private String menuDesc; //菜单描述

    @Column(length = 45)
    private String icon; //图标

    @Column(length = 45)
    private String event; //触发事件

    @Column(length = 1)
    private String menuPosition; //菜单位置 1 为工具栏菜单  2为表格行内菜单

    @Column(length = 45, nullable = false)
    private String menuType; //菜单类型

    @Column(length = 11, nullable = false)
    private Long menuLevel; //菜单级别

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Menu parent; //上级菜单


    @JsonBackReference
    @OneToMany
    @JoinColumn(name = "id")
    private Set<Menu> menus = new HashSet<Menu>();

    @Column(length = 11, nullable = false)
    private Long sortNo; //排序

    @Column(length = 1, nullable = false)
    private String status; //状态
}


