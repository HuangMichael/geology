package com.yhb.controller.auth;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.Area;
import com.yhb.domain.area.AreaTree;
import com.yhb.domain.menu.Menu;
import com.yhb.domain.role.Role;
import com.yhb.domain.user.User;
import com.yhb.service.area.AreaSearchService;
import com.yhb.service.area.AreaService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.menu.MenuSearchService;
import com.yhb.service.menu.MenuService;
import com.yhb.service.role.RoleService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.RedisConfig;
import com.yhb.utils.RedisUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by huangbin on  2017-08-13 15:18:01
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;
    @Autowired
    CommonDataService commonDataService;
    @Autowired
    MenuService menuService;
    @Autowired
    MenuSearchService menuSearchService;
    @Autowired
    RoleService roleService;


    /**
     * @return 授权给指定角色
     */
    @ResponseBody
    @RequestMapping(value = "/auth4Role", method = RequestMethod.POST)
    public ReturnObject auth4Role(@RequestParam("roleId") Long roleId, @RequestParam("menusIds") String menusId) {
        boolean result = menuService.auth4Role(roleId, menusId);
        //如果授权成功后  清除缓存
        //根据角色 将该角色用户中的所有缓存清除
        Role role = roleService.findById(roleId);
        List<User> userList = role.getUserList();
        for (User u : userList) {
            log.info("userMenus" + u.getId());

            try {
                Object str = RedisUtils.get("userMenus" + u.getId());
                if (str != null) {
                    RedisUtils.del("userMenus" + u.getId());
                }
            } catch (Exception e) {

                e.printStackTrace();
            }


        }
        //查询出所有的用户id
        return commonDataService.getReturnType(result, "系统授权成功", "系统授权失败");
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
}
