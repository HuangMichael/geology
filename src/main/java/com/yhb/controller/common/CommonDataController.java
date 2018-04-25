package com.yhb.controller.common;


import com.yhb.service.common.CommonDataService;
import com.yhb.utils.StringUtils;
import com.yhb.vo.app.LongLat;
import com.yhb.vo.app.Mercator;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 基础控制器
 */
@Controller
@Data
@RequestMapping("/commonData")
public class CommonDataController {

    protected Log log = LogFactory.getLog(this.getClass());


    @Autowired
    CommonDataService commonDataService;

    /**
     * @param tableName 表名称
     * @param id        id
     * @return 获取中心坐标并且转换为WGS坐标
     */
    @RequestMapping(value = "/getFeatureCenter", method = RequestMethod.POST)
    @ResponseBody
    public Mercator convertMercator(@RequestParam("tableName") String tableName, @RequestParam("id") Long id) {
        return commonDataService.convertMercator(tableName, id);
    }


}

