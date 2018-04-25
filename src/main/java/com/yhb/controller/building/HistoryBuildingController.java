package com.yhb.controller.building;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaBuilding.HistoryBuilding;
import com.yhb.domain.areaBuilding.VHistoryBuilding;
import com.yhb.service.building.HistoryBuildingService;
import com.yhb.service.building.VHistoryBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by huangbin on 2017/5/8 0008.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/historyBuilding")
public class HistoryBuildingController extends BaseController {

    @Autowired
    HistoryBuildingService historyBuildingService;


    @Autowired
    VHistoryBuildingService vHistoryBuildingService;


    /**
     * @param id 对象id
     * @return 围垦信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HistoryBuilding findById(@PathVariable("id") Long id) {
        return historyBuildingService.findById(id);
    }


    /**
     * @return 查询所有的围垦信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<VHistoryBuilding> findAll() {
        return vHistoryBuildingService.findAll();
    }

    /**
     * @return 查询所有的围垦信息，按照年份求和
     */
    @RequestMapping(value = "/findAllGroupByPeriod", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> findAllGroupByPeriod() {
        return vHistoryBuildingService.findAllGroupByPeriod();
    }
//
//    /**
//     * @return 查询所有的围垦信息
//     */
//    @RequestMapping(value = "/findByPeriod/{period}", method = RequestMethod.GET)
//    @ResponseBody
//    public List<HistoryBuilding> findByPeriod(@PathVariable("period") String period) {
//        return historyBuildingService.findByPeriodOrderBySortNo(period);
//    }


    /**
     * @return 绘制地图
     */
    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "HistoryBuilding/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return historyBuildingService.findMaxId();
    }



    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findByPeriod/{period}", method = RequestMethod.GET)
    @ResponseBody
    public List<VHistoryBuilding> findByPeriod(@PathVariable("period") String period) {
        return vHistoryBuildingService.findByPeriod(period);
    }

}



