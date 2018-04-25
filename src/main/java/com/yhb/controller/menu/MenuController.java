package com.yhb.controller.menu;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.location.Location;
import com.yhb.domain.menu.AuthMenu;
import com.yhb.domain.menu.Menu;
import com.yhb.domain.menu.MenuTree;
import com.yhb.domain.menu.TreeNode;
import com.yhb.domain.person.Person;
import com.yhb.domain.user.User;
import com.yhb.service.menu.MenuSearchService;
import com.yhb.service.menu.MenuService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.SessionUtil;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2017/8/7.
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;
    @Autowired
    MenuService menuService;
    @Autowired
    MenuSearchService menuSearchService;

    String objectName = "菜单信息";


    /**
     * 分页查询
     *
     * @param current      当前页
     * @param rowCount     每页条数
     * @param searchPhrase 查询关键字
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public MyPage data(HttpServletRequest request,
                       @RequestParam(value = "current", defaultValue = "0") int current,
                       @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                       @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortServiceWithSelectedIds(menuSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    /**
     * @param menu
     * @return 保存资源信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(Menu menu) {
        menu = menuService.save(menu);
        return super.save(objectName, menu);
    }

    /**
     * @return 查询所有的资源信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Menu> findAll() {
        return menuService.findAll();
    }

    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Menu findById(@PathVariable("id") Long id) {
        return menuService.findById(id);
    }

    /**
     * @param pId
     * @return 根据父id查询孩子节点
     */
    @RequestMapping("/findByParentId/{pId}")
    @ResponseBody
    public List<Menu> findByParentId(@PathVariable("pId") Long pId) {
        return menuService.findByParentId(pId);
    }

    /**
     * @param id
     * @return 根据id删除资源信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return menuService.delete(id);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return menuService.selectAllIds();
    }

    /**
     * @return 所有资源转变为zTree的形式
     */
    @RequestMapping("/findMenuTree")
    @ResponseBody
    public List<MenuTree> findMenuTree() {
        return menuService.findMenuTree();
    }


    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        List<Menu> dataList = menuSearchService.findByConditions(param, paramsNum, selectedIds);
        menuService.setDataList(dataList);
        menuService.exportExcel(request, response, docName, titles, colNames);
    }


    /**
     * 根据id查询父节点的信息
     *
     * @return 父节点
     */
    @RequestMapping("/findParentById/{id}")
    @ResponseBody
    public Menu findParentById(@PathVariable("id") Long id) {
        return menuService.findParentById(id);
    }

    /**
     * 根据位置编码locCode查询父节点的信息
     *
     * @return 父节点
     */
    @RequestMapping("/findParentByMenuNo/{menuNo}")
    @ResponseBody
    public Menu findParentByLocCode(@PathVariable("menuNo") String menuNo) {
        return menuService.findParentByMenuNo(menuNo);
    }


    /**
     * @param pid 父节点id
     * @return 查询菜单的子菜单列表
     */
    @RequestMapping("/findSubMenusByParentId/{pid}")
    @ResponseBody
    public List<Menu> findSubMenusByParentId(@PathVariable("pid") Long pid) {
        return menuService.findByParentId(pid);
    }


    /**
     * @param userId 用户id
     * @return 根据用户id获取用户所具有的菜单权限
     */
    @RequestMapping(value = "/findAuthMenuByUserId/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeNode> getMenusByUser(@PathVariable("userId") Long userId) {
        return menuService.getMenusByUser(userId);
    }


    /**
     * @param session      当前用户会话
     * @param appName      当前访问应用的名称 唯一
     * @param menuPosition 菜单位置
     * @return 根据用户id获取用户所具有的菜单权限
     */
    @RequestMapping(value = "/requestAppMenus", method = RequestMethod.POST)
    @ResponseBody
    public List<AuthMenu> requestAppMenus(HttpSession session, @RequestParam("appName") String appName, @RequestParam("menuPosition") String menuPosition) {
        List<AuthMenu> authMenuList;
        User user = (User) session.getAttribute("user");
        Long userId = null;
        if (user != null) {
            userId = user.getId();
        }
        authMenuList = menuService.findByUserIdAndAppNameAndPosition(userId, appName, menuPosition);
        for (AuthMenu auth : authMenuList) {
            log.info("auth---------" + auth.toString());
        }
        return authMenuList;
    }
}