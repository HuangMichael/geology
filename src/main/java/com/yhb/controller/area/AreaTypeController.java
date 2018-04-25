package com.yhb.controller.area;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.AreaType;
import com.yhb.service.area.AreaTypeService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
@Controller
@RequestMapping("/areaType")
public class AreaTypeController  extends BaseController {
    @Autowired
    AreaTypeService areaTypeService;

    String objectName = "区块类型信息";
    /**
     * @param areaType
     * @return 保存区块类型信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(AreaType areaType) {
        areaTypeService.save(areaType);
        return super.save(objectName, areaType);
    }

    /**
     * @return 查找所有的区块信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<AreaType> findAll() {
        return areaTypeService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public AreaType findById(@PathVariable("id") Long id) {
        return areaTypeService.findById(id);
    }

    /**
     * @param id
     * @return 根据id删除区块信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaTypeService.delete(id);
    }


    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaTypeService.selectAllIds();
    }
}
