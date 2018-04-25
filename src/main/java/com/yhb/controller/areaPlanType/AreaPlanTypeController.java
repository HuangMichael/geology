package com.yhb.controller.areaPlanType;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaPlanType.AreaPlanType;
import com.yhb.service.areaPlan.AreaPlanTypeService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by huangbin on 2017/5/7 0004.
 */
@Controller
@RequestMapping("/areaPlanType")
public class AreaPlanTypeController extends BaseController {

    @Autowired
    AreaPlanTypeService areaPlanTypeService;
    String objectName = "围垦计划类型信息";


    /**
     * @param areaPlanType
     * @return 保存规划类型
     */
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject save(@RequestParam("areaPlanType") AreaPlanType areaPlanType) {
        areaPlanType = areaPlanTypeService.save(areaPlanType);
        return super.save(objectName, areaPlanType);
    }

    /**
     * @return 区块围垦规划类型信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaPlanType> findAll() {
        return areaPlanTypeService.findAll();
    }

    /**
     * @param id 对象id
     * @return 围垦计划类型信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AreaPlanType findById(@PathVariable("id") Long id) {
        return areaPlanTypeService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 围垦计划类型信息删除操作值对象
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        ReturnObject returnObject;
        AreaPlanType areaPlanType = areaPlanTypeService.findById(id);
        //先查询是否存在该对象
        if (areaPlanType != null) {
            //如果存在提示删除信息操作结果
            areaPlanTypeService.delete(id);
            returnObject = getCommonDataService().getReturnType(areaPlanTypeService.findById(id) == null, objectName + "删除成功！", "删除的" + objectName + "不存在");
        } else {
            //如果不存在提示删除信息不存在
            returnObject = getCommonDataService().getReturnType(false, "", "删除的" + objectName + "不存在");
        }
        return returnObject;
    }


}
