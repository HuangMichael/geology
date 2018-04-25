package com.yhb.utils;

import com.yhb.domain.menu.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


public class MyTreeBuilder {

    /**
     * @param dirs
     * @return
     */
    public static List<TreeNode> buildListToTree(List<TreeNode> dirs) {
        List<TreeNode> roots = findRoots(dirs);
        List<TreeNode> notRoots = (List<TreeNode>) CollectionUtils
                .subtract(dirs, roots);
        for (TreeNode root : roots) {
            root.setMenus(findChildren(root, notRoots));
        }
        return roots;
    }

    /**
     * @param allTreeNodes
     * @return
     */
    public static List<TreeNode> findRoots(List<TreeNode> allTreeNodes) {
        List<TreeNode> results = new ArrayList<>();
        for (TreeNode treeNode : allTreeNodes) {
            boolean isRoot = true;
            for (TreeNode comparedOne : allTreeNodes) {
                if (treeNode.getParentId() == comparedOne.getId()) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                results.add(treeNode);
                treeNode.setRootId(treeNode.getId());
            }
        }
        return results;
    }

    /**
     * @param root
     * @param allTreeNodes
     * @return
     */
    private static List<TreeNode> findChildren(TreeNode root, List<TreeNode> allTreeNodes) {
        List<TreeNode> children = new ArrayList<>();

        for (TreeNode comparedOne : allTreeNodes) {
            if (comparedOne.getParentId() == root.getId()) {
                comparedOne.setParentId(root.getId());
                children.add(comparedOne);
            }
        }
        List<TreeNode> notChildren = (List<TreeNode>) CollectionUtils.subtract(allTreeNodes, children);
        for (TreeNode child : children) {
            List<TreeNode> tmpChildren = findChildren(child, notChildren);
            if (tmpChildren == null || tmpChildren.size() < 1) {
                child.setLeaf(true);
            } else {
                child.setLeaf(false);
            }
            child.setMenus(tmpChildren);
        }
        return children;
    }
}