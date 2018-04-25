package com.yhb.controller.geo;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.geoBaseMap.GeoBaseMap;
import com.yhb.domain.person.Person;
import com.yhb.service.geo.GeoBaseMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by huangbin on 2017-08-18 09:35:49
 * 底图图层控制器
 */
@RestController
@RequestMapping("/geoBaseMap")
public class GeoBaseMapController extends BaseController {


    @Autowired
    GeoBaseMapService geoBaseMapService;
    /**
     * @return 查询所有底图信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<GeoBaseMap> findAll() {
        return geoBaseMapService.findAll();
    }


    /**
     * @return 根据id查询底图信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public GeoBaseMap findById(@PathVariable("id") Long id) {
        return geoBaseMapService.findById(id);
    }


}
