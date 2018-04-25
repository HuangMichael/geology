package com.yhb.controller;


import com.yhb.controller.common.BaseController;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.user.UserService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.Md5Utils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class LoginController extends BaseController {


    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String logout() {
        return "index";

    }


    /**
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject login(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("password") String password) {
        //根据用户名和密码查询用户信息
        //如果返回用户信息User
        try {
            password = Md5Utils.md5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userService.findByUserNameAndPasswordAndStatus(request, userName, password, ConstantUtils.STATUS_YES);
    }

    @RequestMapping("/404")
    public String page404() {
        return "error/page404";
    }

    @RequestMapping("/400")
    public String page400() {
        return "error/page400";
    }

    @RequestMapping("/500")
    public String page500() {
        return "error/page500";
    }
}
