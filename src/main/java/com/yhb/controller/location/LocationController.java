package com.yhb.controller.location;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.location.Location;
import com.yhb.domain.location.LocationTree;
import com.yhb.service.location.LocationSearchService;
import com.yhb.service.location.LocationService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import com.yhb.vo.area.Area4Sel;
import com.yhb.vo.location.Location4Sel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by llm on 2017/6/6 0006.
 */
@Controller
@RequestMapping("/location")
public class LocationController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;
    @Autowired
    LocationService locationService;

    @Autowired
    LocationSearchService locationSearchService;

    String objectName = "位置信息";

    /**
     * 分页查询
     *
     * @param current      当前页
     * @param rowCount     每页条数
     * @param searchPhrase 查询关键字
     * @return
     */
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public MyPage data(HttpServletRequest request,
                       @RequestParam(value = "current", defaultValue = "0") int current,
                       @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                       @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortServiceWithSelectedIds(locationSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds,
                            @RequestParam(value = "sort", required = false) String[] sort) {
        List<Location> dataList = locationSearchService.findByConditions(param, paramsNum, selectedIds);
        locationSearchService.setDataList(dataList);
        locationSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * 保存位置信息
     *
     * @param location 位置
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(Location location) {
        location = locationService.save(location);
        return super.save(objectName, location);
    }

    /**
     * 根据id查询父节点的信息
     *
     * @return 父节点
     */
    @RequestMapping("/findParentById/{id}")
    @ResponseBody
    public Location findParentById(@PathVariable("id") Long id) {
        return locationService.findParentById(id);
    }

    /**
     * 根据位置编码locCode查询父节点的信息
     *
     * @return 父节点
     */
    @RequestMapping("/findParentByLocCode/{locCode}")
    @ResponseBody
    public Location findParentByLocCode(@PathVariable("locCode") String locCode) {
        return locationService.findParentByLocCode(locCode);
    }

    /**
     * 根据父节点的id查询所有子位置，查询所有的子孙节点
     *
     * @return 所有位置
     */
    @RequestMapping("/findLocsByLocCode/{locCode}")
    @ResponseBody
    public List<Location> findLocsByLocCode(@PathVariable("locCode") String locCode) {
        return locationService.findLocsByLocCode(locCode);
    }


    /**
     * 根据parent.id查询江苏省下的所有市或者某市下的所有区县，查询所有的直接子节点，不包括孙子节点
     *
     * @return 所有市
     */
    @RequestMapping("/findLocsByParentId/{pId}")
    @ResponseBody
    public List<Location> findLocsByParentId(@PathVariable("pId") Long pId) {
        return locationService.findLocsByParentId(pId);
    }

    /**
     * @param level 位置级别
     * @return 查询位置级别小于level的所有位置列表
     */
    @RequestMapping("/findByLocLevelLessThan/{level}")
    @ResponseBody
    public List<Location> findByLocLevelLessThan(@PathVariable("level") Long level) {
        return locationService.findByLocLevelLessThan(level);
    }

    /**
     * @param level 位置级别
     * @return 查询位置级别等于该level的所有位置列表
     */
    @RequestMapping("/findByLocLevel/{level}")
    @ResponseBody
    public List<Location> findByLocLevel(@PathVariable("level") Long level) {
        return locationService.findByLocLevel(level);
    }

    /**
     * 查询所有位置
     *
     * @return 所有位置
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Location> findAll() {
        return locationService.findAll();
    }

    /**
     * @return 所有位置变为ztree的形式
     */
    @RequestMapping("/findTree")
    @ResponseBody
    public List<LocationTree> findTreeByAuthKey(HttpSession session) {
        return locationService.findTreeByAuthKey(session);
    }


    /**
     * @return 查询所有省市，按照编号排序
     */
    @RequestMapping("/findAll4Sel")
    @ResponseBody
    public List<Location4Sel> findAll4Sel() {
        return locationService.findAll4Sel();
    }


    /**
     * 根据id查询
     *
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Location findById(@PathVariable("id") Long id) {
        return locationService.findById(id);
    }

    /**
     * @param pLocName 父级位置的名称
     * @return 根据父级位置的名称查询，返回子位置的列表
     */
    @RequestMapping("/findLocsByPLocName/{pLocName}")
    @ResponseBody
    public List<Location> findLocsByPLocName(@PathVariable("pLocName") String pLocName) {
        Location pLoc = locationService.findByLocName(pLocName);
        if (pLoc == null) {
            return null;
        }
        return locationService.findLocsByParentId(pLoc.getId());
    }

    /**
     * 根据位置名称查询
     *
     * @param locName 位置名称
     * @return
     */
    @RequestMapping("/findByLocName/{locName}")
    @ResponseBody
    public Location findByLocName(@PathVariable("locName") String locName) {
        return locationService.findByLocName(locName);
    }

    /**
     * @param id 根据ID删除位置信息
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return locationService.delete(id);
    }


    /**
     * 根据父节点的位置编码locCode查询所有子位置，查询所有的子孙节点
     *
     * @return 所有位置
     */
    @RequestMapping("/showSubList/{locCode}")
    @ResponseBody
    public MyPage showSubList(@PathVariable("locCode") String locCode) {
        return null;
    }


}
