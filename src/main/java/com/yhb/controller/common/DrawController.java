package com.yhb.controller.common;


import com.yhb.utils.StringUtils;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 基础控制器
 */
@Controller
@Data
public abstract class DrawController {

    protected Log log = LogFactory.getLog(this.getClass());


    @RequestMapping(value = "/draw")
    public String draw() {
        String controllerName = this.getClass().getSimpleName().split("Controller")[0];
        String url = "/" + StringUtils.lowerCaseCamel(controllerName) + "/draw";
        return url;
    }

    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public abstract Long findMaxId();


}

