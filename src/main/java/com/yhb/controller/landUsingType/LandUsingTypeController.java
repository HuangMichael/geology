package com.yhb.controller.landUsingType;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.types.LandUsingType;
import com.yhb.service.landUsingType.LandUsingTypeService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Controller
@RequestMapping("/landUsingType")
public class LandUsingTypeController extends BaseController {
    @Autowired
    LandUsingTypeService landUsingTypeService;
    String objectName = "用地类型信息";

    /**
     * @return 查找所有的用地类型信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<LandUsingType> findAll() {
        return landUsingTypeService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询用地类型信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public LandUsingType findById(@PathVariable("id") Long id) {
        return landUsingTypeService.findById(id);
    }

    /**
     * @param id
     * @return 根据id删除用地类型信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return landUsingTypeService.delete(id);
    }

}
