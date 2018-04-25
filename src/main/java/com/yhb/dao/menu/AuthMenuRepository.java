package com.yhb.dao.menu;

import com.yhb.domain.menu.AuthMenu;
import com.yhb.domain.menu.MenuTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangbin on 2017-08-14 15:17:07.
 */
public interface AuthMenuRepository extends JpaRepository<AuthMenu, Long> {
    /**
     * @return 根据用户id查询权限菜单
     */
    List<AuthMenu> findByUserId(Long userId);

    /**
     * @return 根据用户id查询权限菜单
     */
    List<AuthMenu> findByParentId(Long parentId);


    /**
     * @param userId  当前登录用户的id
     * @param appName 应用名称唯一 为描述
     * @return 根据用户id和应用名称查询权限菜单
     */
    List<AuthMenu> findByUserIdAndAppName(Long userId, String appName);


    /**
     * @param userId       当前登录用户的id
     * @param appName      应用名称唯一 为描述
     * @param menuPosition 菜单位置
     * @return 根据用户id和应用名称查询权限菜单
     */
    List<AuthMenu> findByUserIdAndAppNameAndMenuPositionContains(Long userId, String appName, String menuPosition);
}
