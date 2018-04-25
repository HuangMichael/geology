package com.yhb.controller.area;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.AreaFunctionType;
import com.yhb.service.area.AreaFunctionTypeService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
@Controller
@RequestMapping("/areaFunctionType")
public class AreaFunctionTypeController extends BaseController {

    @Autowired
    AreaFunctionTypeService areaFunctionTypeService;

    String objectName = "区块功能类型信息";

    /**
     * @param areaFunctionType
     * @return 保存区块功能类型信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(AreaFunctionType areaFunctionType) {
        areaFunctionTypeService.save(areaFunctionType);
        return super.save(objectName, areaFunctionType);
    }

    /**
     * @return 查找所有的区块信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<AreaFunctionType> findAll() {
        return areaFunctionTypeService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public AreaFunctionType findById(@PathVariable("id") Long id) {
        return areaFunctionTypeService.findById(id);
    }

    /**
     * @param id
     * @return 根据id删除区块信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaFunctionTypeService.delete(id);
    }


    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaFunctionTypeService.selectAllIds();
    }
}
