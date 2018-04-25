package com.yhb.domain.menu;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017-08-14 14:38:17
 * 菜单角色用户视图
 */
@Entity
@Table(name = "V_AUTH_MENU")
@Data
public class AuthMenu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //序号
    private String menuNo; //菜单序号
    private String menuName; //菜单名称
    private String menuDesc; //菜单描述
    private String appName; //应用名称
    private String event;   //事件
    private String menuPosition;   //菜单位置
    private String icon;   //图标
    private Long menuLevel; //菜单级别
    private Long parentId; //父节点编号
    private Long roleId; //角色id
    private Long userId; //用户id
}
