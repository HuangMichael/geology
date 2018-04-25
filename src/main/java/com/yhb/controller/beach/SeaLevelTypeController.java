package com.yhb.controller.beach;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.beach.SeaLevelType;
import com.yhb.service.beach.SeaLevelTypeService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@Controller
@RequestMapping("/seaLevelType")
public class SeaLevelTypeController extends BaseController {
    @Autowired
    SeaLevelTypeService seaLevelTypeService;

    String objectName = "潮位类型信息";

    /**
     * @param seaLevelType
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(SeaLevelType seaLevelType) {
        seaLevelType = seaLevelTypeService.save(seaLevelType);
        return super.save(objectName, seaLevelType);
    }

    /**
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<SeaLevelType> findAll() {
        return seaLevelTypeService.findAll();
    }


    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public SeaLevelType findById(@PathVariable("id") Long id) {
        return seaLevelTypeService.findById(id);
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return seaLevelTypeService.delete(id);
    }
}
