package com.yhb.controller.user;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.AreaMedia;
import com.yhb.domain.location.Location;
import com.yhb.domain.person.Person;
import com.yhb.domain.user.User;
import com.yhb.service.location.LocationService;
import com.yhb.service.person.PersonService;
import com.yhb.service.user.UserSearchService;
import com.yhb.service.user.UserService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.SessionUtil;
import com.yhb.utils.annotation.DataAttr;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.Mercator;
import com.yhb.vo.app.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/user")
@Slf4j
@DataAttr(objectName = "用户信息")
public class UserController extends BaseController {
    //复合查询条件参数个数

    @Autowired
    UserService userService;
    @Autowired
    UserSearchService userSearchService;

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
        return new PageUtils().searchBySortServiceWithSelectedIds(userSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param id
     * @param userName
     * @param password
     * @param status
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "userName") String userName,
                             @RequestParam(value = "personName") String personName,
                             @RequestParam(value = "birthDate") String birthDate,
                             @RequestParam(value = "gender") Long gender,
                             @RequestParam(value = "department") String department,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "status") String status) {
        User user = userService.save(id, userName, personName, birthDate, gender, department, password, status);
        return super.save(objectName, user);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() {
        return userService.findAll();
    }


    @RequestMapping("/findById/{id}")
    @ResponseBody
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    /**
     * @param userName
     * @return 根据用户名称查询
     */
    @RequestMapping(value = "/findByUserName", method = RequestMethod.POST)
    @ResponseBody
    public User findByUserName(@RequestParam("userName") String userName) {
        return userService.findByUserName(userName);
    }

    /**
     * @param userName
     * @return 编辑用户信息时，根据前端传过来的用户输入的新用户名称和id查询是否与其他的用户重名。如果重复，返回一个User；不重复返回null
     */
    @RequestMapping(value = "/findUserDuplicateByUserNameAndId", method = RequestMethod.POST)
    @ResponseBody
    public User findUserDuplicateByUserNameAndId(@RequestParam("id") Long id, @RequestParam("userName") String userName) {
        return userService.findUserDuplicateByUserNameAndId(id, userName);
    }

    /**
     * @param id 根据ID删除用户信息
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return userService.delete(id);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[],
                            @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        List<User> dataList = userSearchService.findByConditions(param, paramsNum, selectedIds);
        userService.setDataList(dataList);
        userService.exportExcel(request, response, docName, titles, colNames);
    }


    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return userService.selectAllIds();
    }

    /**
     * @return 查询不是参数值id的所有其他id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectOtherIds/{id}", method = RequestMethod.GET)
    public List<Long> selectOtherIds(@PathVariable("id") Long id) {
        return userService.selectOtherIds(id);
    }

    /**
     * @param userName 用户名
     * @return 根据用户名查询人员信息
     */
    @ResponseBody
    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public Person findPersonByUserName(@RequestParam("userName") String userName) {
        return userService.findPersonByUserName(userName);
    }


    /**
     * @param request
     * @return 获得当前登录的用户
     */
    @ResponseBody
    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    public User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user;
    }


    /**
     * @param userName
     * @return 根据用户名称获取用户对象
     */
    @ResponseBody
    @RequestMapping(value = "/findUserByUserName", method = RequestMethod.POST)
    public User findUserByUserName(@RequestParam("userName") String userName) {
        User user = userService.findByUserName(userName);
        return user;
    }


    /**
     * @param locId 位置id
     * @return 查询该位置下的用户信息
     */
    @RequestMapping(value = "/getLocationUsers/{locId}", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getLocationUsers(@PathVariable("locId") Long locId) {
        return userService.findByLocation(locId);
    }

    /**
     * @param locId 位置id
     * @return 查询该位置下的用户列表，分页显示
     */
    @RequestMapping(value = "/getLocationUsersMyPage", method = RequestMethod.POST)
    @ResponseBody
    public MyPage getLocationUsers(@RequestParam("locId") Long locId,
                                   @RequestParam(value = "current", defaultValue = "0") int current,
                                   @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                                   @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        return userService.getLocationUsersMyPage(locId, current, rowCount, searchPhrase);
    }

    /**
     * 根据用户名和密码验证用户输入的密码password是否正确
     *
     * @param userName
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verifyPassword", method = RequestMethod.POST)
    public ReturnObject verifyPassword(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return userService.verifyPassword(userName, password);
    }

    /**
     * 根据用户名和新密码newPassword修改密码
     *
     * @param userName
     * @param newPassword
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public ReturnObject modifyPassword(@RequestParam("userName") String userName, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        return userService.modifyPassword(userName, oldPassword, newPassword);
    }

    /**
     * 查询该位置下未进行授权的用户信息列表
     *
     * @param locId
     * @return
     */
    @RequestMapping(value = "/popUsers/{locId}", method = RequestMethod.GET)
    @ResponseBody
    public List<User> popUsers(@PathVariable("locId") Long locId) {
        List<User> usersNotInLocation = userService.findByLocationIsNot(locId);
        return usersNotInLocation;
    }

    /**
     * 查询该位置下未进行授权的用户信息列表，分页显示
     *
     * @param locId 位置id
     * @return
     */
    @RequestMapping(value = "/popUsersMyPage", method = RequestMethod.POST)
    @ResponseBody
    public MyPage popUsersMyPage(@RequestParam("locId") Long locId,
                                 @RequestParam(value = "current", defaultValue = "0") int current,
                                 @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                                 @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        return userService.popUsersMyPage(locId, current, rowCount, searchPhrase);
    }

    /**
     * @param session
     * @return
     */
    @RequestMapping(value = "/getUserPosition", method = RequestMethod.GET)
    @ResponseBody
    public Mercator getUserPositionCenter(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Long locId = user.getLocation().getId();
        locId = (locId != null) ? locId : 165;
        return userService.getUserPositionCenter(locId);
    }


    /**
     * 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     * <p>
     * huangbin  overide
     *
     * @param file
     * @param request
     * @param session
     * @param userName
     * @return 返回项目多媒体信息
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFileAndSavePath", method = RequestMethod.POST)
    @ResponseBody
    public User uploadFileAndSavePath(@RequestParam("file") MultipartFile file,
                                           HttpServletRequest request, HttpSession session, @RequestParam("userName") String userName) throws Exception {
        return userService.uploadFileAndSavePath(file,request, session, userName);
    }


}
