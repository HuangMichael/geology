package com.yhb.domain.menu;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/7.
 * 资源信息视图
 */
@Entity
@Table(name = "V_MENU_TREE")
@Data
public class MenuTree implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //序号
    private String code; //菜单编码
    private Long pId; //父节点序号
    private String name; //资源名称
    private String t;   //描述
}
