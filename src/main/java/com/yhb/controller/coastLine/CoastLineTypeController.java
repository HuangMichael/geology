package com.yhb.controller.coastLine;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.coastline.CoastLineType;
import com.yhb.service.coastLine.CoastLineTypeService;
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
@RequestMapping("/coastLineType")
public class CoastLineTypeController extends BaseController {
    @Autowired
    CoastLineTypeService coastLineTypeService;

    String objectName = "海岸线类型信息";

    /**
     * @param coastLineType
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(CoastLineType coastLineType) {
        coastLineType = coastLineTypeService.save(coastLineType);
        return super.save(objectName, coastLineType);
    }

    /**
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<CoastLineType> findAll() {
        return coastLineTypeService.findAll();
    }


    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public CoastLineType findById(@PathVariable("id") Long id) {
        return coastLineTypeService.findById(id);
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return coastLineTypeService.delete(id);
    }
}
