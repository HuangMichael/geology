package com.yhb.domain.role;


/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yhb.domain.menu.Menu;
import com.yhb.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 角色信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_ROLE")
@Data
public class Role implements Serializable {
    @Id
    @SequenceGenerator(name = "ROLE_GENERATOR", sequenceName = "SEQ_ROLE", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String roleName; //角色名称

    @Column(length = 200)
    private String roleDesc; //角色描述

    @Column(length = 1)
    private String status; //状态

    //角色用户多对多关系
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_user", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> userList;
    
    //角色菜单多对多关系
    @JsonBackReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<Menu> menuList;
}



