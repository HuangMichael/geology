package com.yhb.service.menu;

import com.yhb.dao.menu.AuthMenuRepository;
import com.yhb.dao.menu.MenuRepository;
import com.yhb.dao.menu.MenuTreeRepository;
import com.yhb.dao.role.RoleRepository;
import com.yhb.dao.user.UserRepository;
import com.yhb.domain.menu.AuthMenu;
import com.yhb.domain.menu.Menu;
import com.yhb.domain.menu.MenuTree;
import com.yhb.domain.menu.TreeNode;
import com.yhb.domain.role.Role;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.MyTreeBuilder;
import com.yhb.utils.StringUtils;
import com.yhb.utils.TreeBuilder;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huangbin on 2017/8/7.
 * 加入缓存服务
 */
@Service
@CacheConfig
public class MenuService extends BaseService {

    @Autowired
    AuthMenuRepository authMenuRepository;
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuTreeRepository menuTreeRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    RoleRepository roleRepository;


    @Autowired
    UserRepository userRepository;

    /**
     * @param menu
     * @return 保存资源信息
     */
    @CachePut(value = "menus", key = "#menu.getMenuNo()")
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    /**
     * @return 查询所有的资源信息
     */


    @Cacheable(value = "menus", key = "'menus'")
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    /**
     * @param id
     * @return 根据id查询资源信息
     */

    public Menu findById(Long id) {
        return menuRepository.findOne(id);
    }


    /**
     * @param id 根据ID删除资源信息
     */
    public ReturnObject delete(Long id) {
        Menu menu = menuRepository.findOne(id);
        if (menu == null) {
            return commonDataService.getReturnType(menu != null, "", "id为" + id + "的资源信息不存在,请确认！");
        }

        try {
            menuRepository.delete(menu);
            //再查询一次查看是否删除
            Menu menu1 = menuRepository.findOne(id);
            return commonDataService.getReturnType(menu1 == null, "资源信息删除成功！", "资源信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "", "资源信息删除失败！");
        }

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return menuRepository.selectAllIds();
    }

    /**
     * @return 所有资源转变为ztree的形式
     */
    public List<MenuTree> findMenuTree() {
        return menuTreeRepository.findAll();
    }


    /**
     * @param id
     * @return 该位置的父节点
     */
    public Menu findParentById(Long id) {
        Menu menu = menuRepository.findOne(id);
        return menu.getParent();
    }

    /**
     * @param menuNo 该位置的位置编码
     * @return 该位置的父节点
     */
    public Menu findParentByMenuNo(String menuNo) {
        Menu parentMenu = null;
        List<Menu> menuList = menuRepository.findByMenuNo(menuNo);
        if (!menuList.isEmpty()) {
            parentMenu = menuList.get(0).getParent();
        }
        return parentMenu;
    }


    /**
     * @param pid
     * @return 根据菜单id查询子菜单列表
     */
    public List<Menu> findByParentId(Long pid) {
        return menuRepository.findByParent_Id(pid);
    }


    /**
     * @param roleId
     * @param menusIds
     * @return 根据菜单id查询子菜单列表
     */
    public Boolean auth4Role(Long roleId, String menusIds) {
        //根据roleId查询出role
        Role role = roleRepository.findOne(roleId);
        //处理分割字符串  并将菜单权限设置给role;
        String[] menusArray = menusIds.split(",");
        List<String> menuIdArray = Arrays.asList(menusArray);
        List<Long> idList = new ArrayList<Long>();
        for (String idStr : menuIdArray) {
            idList.add(Long.parseLong(idStr));
        }
        List<Menu> menuList = menuRepository.findByIdIn(idList);
        role.setMenuList(menuList);
        role = roleRepository.save(role);
        return !role.getMenuList().isEmpty();
    }


    /**
     * @return 根据用户id查询该用户所在的角色拥有的权限
     */
    @Cacheable(value = "userMenus", key = "'userMenus'+#userId")
    public List<TreeNode> getMenusByUser(Long userId) {
        //根据用户的id获取用户所具有的角色
        //根据角色获取角色对应的菜单权限
        //将用户所具有的所有的菜单权限进行合并  封装为一个list
        List<TreeNode> menuList = new ArrayList<>();
        List<AuthMenu> authMenus = authMenuRepository.findByUserId(userId);
        authMenus = removeDuplicate(authMenus);
        TreeNode treeNode;
        for (AuthMenu authMenu : authMenus) {
                treeNode = new TreeNode();
                treeNode.setId(authMenu.getId());
                treeNode.setEvent(authMenu.getEvent());
                treeNode.setIcon(authMenu.getIcon());
                treeNode.setMenuLevel(authMenu.getMenuLevel());
                treeNode.setMenuName(authMenu.getMenuName());
                if (authMenu.getParentId() != null) {
                    treeNode.setParentId(authMenu.getParentId());
                }
                menuList.add(treeNode);
            }
        return  MyTreeBuilder.buildListToTree(menuList);
    }


    /**
     * @param userId
     * @param appName
     * @param menuPosition
     * @return 根据应用名称和当前登录的用户查询该应用中的权限
     */
    public List<AuthMenu> findByUserIdAndAppNameAndPosition(Long userId, String appName, String menuPosition) {
        return authMenuRepository.findByUserIdAndAppNameAndMenuPositionContains(userId, appName, menuPosition);
    }


    /**
     * @param authMenus 权限菜单集合
     * @return 去重复
     */
    private static List<AuthMenu> removeDuplicate(List<AuthMenu> authMenus) {
        Set<AuthMenu> set = new TreeSet<AuthMenu>(new Comparator<AuthMenu>() {
            @Override
            public int compare(AuthMenu o1, AuthMenu o2) {
                //字符串,则按照asicc码升序排列
                return o1.getId().compareTo(o2.getId());
            }
        });
        set.addAll(authMenus);
        return new ArrayList<>(set);
    }


    /**
     * @param appName
     * @param menuType
     * @param status
     * @return 根据应用名称和当前登录的用户查询该应用中的权限
     */
    public Menu findByAppNameAndMenuTypeAndStatus(String appName, String menuType, String status) {
        return menuRepository.findByAppNameAndMenuTypeAndStatus(appName, menuType, status);
    }

    /**
     * @param appName
     * @param menuType
     * @param status
     * @return 根据应用名称和当前登录的用户查询该应用中的权限
     */
    public Menu findByEventAndMenuTypeAndStatus(String appName, String menuType, String status) {
        return menuRepository.findByEventAndMenuTypeAndStatus(appName, menuType, status);
    }


}

