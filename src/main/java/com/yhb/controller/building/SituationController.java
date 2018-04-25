package com.yhb.controller.building;


import com.yhb.controller.common.BaseController;
import com.yhb.dao.location.LocationBuildingRepository;
import com.yhb.domain.location.LocationBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 围垦现状控制器类
 *
 * @author huangbin
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/situation")
public class SituationController extends BaseController {


    @Autowired
    LocationBuildingRepository locationBuildingRepository;


    /**
     * @param locId 位置id
     * @return 根据位置id获取围垦现状描述
     */
    @RequestMapping("/getWkDescByLocId/{locId}")
    @ResponseBody
    public LocationBuilding getWkDescByLocId(@PathVariable("locId") Long locId) {
        List<LocationBuilding> locationBuildingList = locationBuildingRepository.findByLocation_Id(locId);
        return locationBuildingList.get(0);
    }
}
