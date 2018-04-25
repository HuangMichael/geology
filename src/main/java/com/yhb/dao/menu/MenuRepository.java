package com.yhb.dao.menu;

import com.yhb.domain.menu.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

/**
 * Created by huangbin on 2017-08-13 17:38:58
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from Menu a ")
    List<Long> selectAllIds();

    /**
     * @param menuName
     * @return 根据 菜单名称查询菜单
     */
    List<Menu> findByMenuNameContainsAndIdIn(String menuName, List<Long> selectedIds);

    /**
     * @param menuName
     * @return 根据 菜单名称分页查询菜单
     */
    Page<Menu> findByMenuNameContains(String menuName, Pageable pageable);


    /**
     * @param menuNo
     * @return 根据菜单编号查询菜单
     */
    List<Menu> findByMenuNo(String menuNo);


    /**
     * @param pid 菜单id
     * @return 查询子菜单列表
     */
    List<Menu> findByParent_Id(Long pid);


    /**
     * @param idList
     * @return in查询
     */
    List<Menu> findByIdIn(List<Long> idList);


    /**
     * @param appName
     * @param menuType
     * @param status
     * @return
     */
    Menu findByAppNameAndMenuTypeAndStatus(String appName, String menuType, String status);


    /**
     * @param event
     * @param menuType
     * @param status
     * @return
     */
    Menu findByEventAndMenuTypeAndStatus(String event, String menuType, String status);
}
