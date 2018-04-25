package com.yhb.controller.auth;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.location.Location;
import com.yhb.domain.location.LocationTree;
import com.yhb.service.location.LocationSearchService;
import com.yhb.service.location.LocationService;
import com.yhb.service.user.UserService;
import com.yhb.utils.PageUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2017-08-21 10:25:11
 */
@Controller
@RequestMapping("/dataAuth")
public class DataAuthController extends BaseController {

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;
    @Autowired
    LocationSearchService locationSearchService;

    String objectName = "数据授权信息";



    /**
     * @param locationId 位置id
     * @param userIds    用户id字符串
     * @return 将locationId的数据权限授予users
     */
    @RequestMapping(value = "/grantDataAuth", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject grantDataAuth(@RequestParam("locationId") Long locationId, @RequestParam("userIds") String userIds) {
        return userService.grantDataAuth(locationId, userIds);
    }


    /**
     * @return 载入用户页面
     */
    @RequestMapping(value = "/loadUserPage", method = RequestMethod.POST)
    public String loadUserPage() {
        return "/dataAuth/popUsers";
    }


    /**
     * @param userId
     * @return userId用户取消授权
     */
    @RequestMapping(value = "/revokeLoc", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject revokeLoc(@RequestParam("userId") Long userId) {
        Boolean result = userService.revokeLoc(userId);
        return getCommonDataService().getReturnType(result, "用户取消授权成功", "用户取消授权失败");
    }
}
