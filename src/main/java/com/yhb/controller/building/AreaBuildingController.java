package com.yhb.controller.building;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaBuilding.AreaBuilding;
import com.yhb.domain.areaBuilding.VAreaBuiltStat;
import com.yhb.domain.areaBuilding.VProvBuilding;
import com.yhb.domain.areaBuilding.VcityBuilding;
import com.yhb.domain.location.LocationBuilding;
import com.yhb.service.building.AreaBuildingSearchService;
import com.yhb.service.building.AreaBuildingService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2017/5/8 0008.
 */
@Controller
@EnableAutoConfiguration
@RequestMapping("/areaBuilding")
public class AreaBuildingController extends BaseController {

    @Autowired
    AreaBuildingSearchService areaBuildingSearchService;

    @Autowired
    AreaBuildingService areaBuildingService;

    String objectName = "围垦进度信息";



    /**
     * @param session
     * @param id
     * @param areaId
     * @param buildDesc
     * @param beginYear
     * @param endYear
     * @param cityId
     * @param districtId
     * @param buildSize
     * @param nyBuildSize
     * @param jsBuildSize
     * @param stBuildSize
     * @param manager
     * @param status
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(HttpSession session,
                             @RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "areaId") Long areaId,
                             @RequestParam(value = "buildDesc",required = false) String buildDesc,
                             @RequestParam(value = "beginYear", required = false) String beginYear,
                             @RequestParam(value = "endYear", required = false) String endYear,
                             @RequestParam(value = "cityId", required = false) Long cityId,
                             @RequestParam(value = "districtId", required = false) Long districtId,
                             @RequestParam(value = "buildSize", required = false) Double buildSize,
                             @RequestParam(value = "nyBuildSize", required = false) Double nyBuildSize,
                             @RequestParam(value = "jsBuildSize", required = false) Double jsBuildSize,
                             @RequestParam(value = "stBuildSize", required = false) Double stBuildSize,
                             @RequestParam(value = "manager", required = false) String manager,
                             @RequestParam(value = "status") String status) {
        AreaBuilding areaBuilding1 = areaBuildingService.save(session, id, areaId, buildDesc, beginYear, endYear,
                cityId, districtId, buildSize, nyBuildSize, jsBuildSize, stBuildSize, manager, status);
        return super.save(objectName, areaBuilding1);
    }


    /**
     * @param id 对象id
     * @return 围垦信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AreaBuilding findById(@PathVariable("id") Long id) {
        return areaBuildingService.findById(id);
    }

    /**
     * @return 根据areaId查询所有围垦信息
     */
    @RequestMapping(value = "/findByAreaId/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaBuilding> findByAreaId(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findByAreaId(areaId);
    }

    /**
     * @return 查询所有的围垦信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaBuilding> findAll() {
        return areaBuildingService.findAll();
    }

    /**
     * @param id 对象id
     * @return 围垦信息删除操作值对象
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaBuildingService.delete(id);
    }


    /**
     * @param locationId 位置id
     * @return 根据位置查询该位置的围垦现状描述信息
     */
    @RequestMapping(value = "/findDescByLocationId/{locationId}", method = RequestMethod.GET)
    @ResponseBody
    public List<LocationBuilding> findDescByLocationId(@PathVariable("locationId") Long locationId) {
        return areaBuildingService.findDescByLocationId(locationId);
    }

    /**
     * @param locationId 位置id
     * @return 根据位置查询区块信息
     */
    @RequestMapping(value = "/location/{locationId}/areas", method = RequestMethod.GET)
    @ResponseBody
    public List<Area> findAreasByLocationId(@PathVariable("locationId") Long locationId) {
        return areaBuildingService.findAreasByLocationId(locationId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块id查询该区块的现状描述信息
     */
    @RequestMapping(value = "/desc/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> findAreaDescById(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findAreaDescById(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    @RequestMapping(value = "/area/areaTypeSize/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> findAreaTypeSizeByAreaId(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findAreaTypeSizeByAreaId(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据求和
     */
    @RequestMapping(value = "/area/areaTypeSizeTotal/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findAreaTypeSizeTotalById(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findAreaTypeSizeTotalByAreaId(areaId);
    }


    /**
     * @return 绘制地图
     */
    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "areaBuilding/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return areaBuildingService.findMaxId();
    }


    /**
     * @return 省级围垦现状统计数据 下钻到市
     */
    @RequestMapping(value = "/buildingProv", method = RequestMethod.GET)
    @ResponseBody
    public List<VProvBuilding> findProvBuildingStat() {
        return areaBuildingService.findProvBuildingStat();
    }


    /**
     * @return 省级围垦现状统计数据 下钻到市
     */
    @RequestMapping(value = "/buildingCity/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<VcityBuilding> findCityBuildingStat(@PathVariable("parentId") Long parentId) {
        return areaBuildingService.findCityBuildingStat(parentId);
    }


    /**
     * @return 省级围垦现状统计数据 下钻到市
     */
    @RequestMapping(value = "/findStatByAreaId/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public  VAreaBuiltStat findByArea(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findStatByAreaId(areaId);
    }
}



