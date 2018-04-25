package com.yhb.utils.search;

/**
 * Created by huangbin on 2017/9/9.
 */


import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class Node implements Serializable {
    private static final long serialVersionUID = -2721191232926604726L;

    private int id;

    private int parentId;

    private Node parent;

    private List<Node> children;

    private String name;

    private int level;

    private int sort;

    private int rootId;

    private String type;

    private boolean isLeaf;

    private String description;

    public Node() {
        super();
    }

    public Node(int id, int parentId, String name) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + parentId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (id != other.id)
            return false;
        if (parentId != other.parentId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Node {id=" + id + ", parentId=" + parentId + ", children="
                + children + ", name=" + name + ", level =" + level + "}";
    }
}

