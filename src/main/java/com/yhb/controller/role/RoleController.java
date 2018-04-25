package com.yhb.controller.role;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.role.Role;
import com.yhb.domain.user.User;
import com.yhb.service.role.RoleSearchService;
import com.yhb.service.role.RoleService;
import com.yhb.service.user.UserService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.annotation.DataAttr;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/role")
@Slf4j
@DataAttr(objectName = "角色信息")
@Data
public class RoleController extends BaseController {
    @Autowired
    RoleService roleService;

    @Autowired
    RoleSearchService roleSearchService;


    int paramsNum = 1;

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
        return new PageUtils().searchBySortServiceWithSelectedIds(roleSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param role 角色信息
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(Role role) {
        role = roleService.save(role);
        return super.save(objectName, role);
    }

    /**
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Role> findAll() {
        return roleService.findAll();
    }


    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Role findById(@PathVariable("id") Long id) {
        return roleService.findById(id);
    }

    /**
     * @param roleName
     * @return
     */
    @RequestMapping(value = "/findByRoleName", method = RequestMethod.POST)
    @ResponseBody
    public Role findByRoleName(@RequestParam("roleName") String roleName) {
        return roleService.findByRoleName(roleName);
    }

    /**
     * @param id
     * @param roleName
     * @return 编辑角色信息时，根据前端传过来的用户输入的新角色名称和id查询是否与其他的角色重名。如果重复，返回一个Role；不重复返回null
     */
    @RequestMapping(value = "/findRoleDuplicateByRoleNameAndId", method = RequestMethod.POST)
    @ResponseBody
    public Role findRoleDuplicateByRoleNameAndId(@RequestParam("id") Long id, @RequestParam("roleName") String roleName) {
        return roleService.findRoleDuplicateByRoleNameAndId(id, roleName);
    }

    /**
     * 根据id删除角色信息
     *
     * @param id 角色id
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return roleService.delete(id);
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
        List<Role> dataList = roleSearchService.findByConditions(param, paramsNum, selectedIds);
        roleService.setDataList(dataList);
        roleService.exportExcel(request, response, docName, titles, colNames);
    }


    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return roleService.selectAllIds();
    }


    /**
     * @param userIds
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addUsers", method = RequestMethod.POST)
    public ReturnObject addUsers(@RequestParam("userIds") String userIds, @RequestParam("roleId") Long roleId) {
        Boolean result = roleService.addUsers(userIds, roleId);
        return getCommonDataService().getReturnType(result, "角色添加用户成功!", "角色添加用户失败!");
    }


    /**
     * @param userIds
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/removeUsers", method = RequestMethod.POST)
    public ReturnObject removeUsers(@RequestParam("userIds") String userIds, @RequestParam("roleId") Long roleId) {
        Boolean result = roleService.removeUsers(userIds, roleId);
        return getCommonDataService().getReturnType(result, "角色移除用户成功!", "角色移除用户失败!");
    }

    /**
     * @param roleId 角色id
     * @return 查询不属于该角色的用户列表
     */
    @ResponseBody
    @RequestMapping(value = "/getOtherUsers/{roleId}", method = RequestMethod.GET)
    public List<User> getOtherUsers(@PathVariable("roleId") Long roleId) {
        return roleService.findUserListNotOfRole(roleId);
    }

    /**
     * @param current      从1开始计数
     * @param rowCount
     * @param searchPhrase
     * @param roleId       角色id
     * @return 查询不属于该角色的用户列表，分页显示-20170822下午
     */
    @ResponseBody
    @RequestMapping(value = "/getOtherUsersMyPage", method = RequestMethod.POST)
    public MyPage getOtherUsersMyPage(@RequestParam(value = "current", defaultValue = "0") int current,
                                      @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                                      @RequestParam(value = "searchPhrase", required = false) String searchPhrase,
                                      @RequestParam("roleId") Long roleId) {
        return roleService.getOtherUsersMyPage(roleId, current, rowCount, searchPhrase);

    }


    /**
     * @param current      从1开始计数
     * @param rowCount
     * @param searchPhrase
     * @param roleId       角色id
     * @return 查询不属于该角色的用户列表，分页显示-20170822下午
     */
    @ResponseBody
    @RequestMapping(value = "/getMyUsersMyPage", method = RequestMethod.POST)
    public MyPage getMyUsersMyPage(@RequestParam(value = "current", defaultValue = "0") int current,
                                   @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                                   @RequestParam(value = "searchPhrase", required = false) String searchPhrase,
                                   @RequestParam("roleId") Long roleId) {
        return roleService.getMyUsersMyPage(roleId, current, rowCount, searchPhrase);

    }
}
