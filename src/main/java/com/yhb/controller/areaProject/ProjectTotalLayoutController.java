package com.yhb.controller.areaProject;

import com.yhb.controller.common.BaseController;
import com.yhb.dao.areaProject.AreaProjectDescRepository;
import com.yhb.domain.areaProject.AreaProjectDesc;
import com.yhb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/projectTotalLayout")
public class ProjectTotalLayoutController extends BaseController {

    @Autowired
    AreaProjectDescRepository areaProjectDescRepository;


    /**
     * @return 根据位置查询项目总体布局描述信息
     */
    @ResponseBody
    @RequestMapping(value = "/layoutDesc/{locId}", method = RequestMethod.GET)
    public AreaProjectDesc getLayoutDescByLocId(@PathVariable("locId") Long locId) {
        return areaProjectDescRepository.findByLocation_IdAndStatusOrderByIdDesc(locId, "1");
    }













}
