package com.yhb.domain.menu;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangbin on 2017-08-14 14:38:17
 * 权限树菜单
 */
@Data
public class TreeNode implements Serializable {


    private long id;

    private long parentId;

    private List<TreeNode> menus;

    private String menuName;

    private long menuLevel;

    private long sort;

    private long rootId;

    private String type;

    private boolean isLeaf;

    private String description;

    private String event;

    private String icon;


    public TreeNode() {
        super();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TreeNode other = (TreeNode) obj;
        if (id != other.id)
            return false;
        if (parentId != other.parentId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Node {id=" + id + ", parentId=" + parentId + ", menus="
                + menus + ", menuName=" + menuName + ", menuLevel =" + menuLevel + "}";
    }
}
