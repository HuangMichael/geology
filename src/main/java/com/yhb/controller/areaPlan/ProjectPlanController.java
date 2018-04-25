package com.yhb.controller.areaPlan;

import com.yhb.controller.common.BaseController;
import com.yhb.dao.areaProject.*;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaProject.*;
import com.yhb.service.area.AreaSearchService;
import com.yhb.service.area.AreaService;
import com.yhb.service.areaProject.AreaProjectService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by huangbin on 2017/5/10 0004.
 */
@Controller
@RequestMapping("/projectPlan")
public class ProjectPlanController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 3;

    @Autowired
    AreaService areaService;
    @Autowired
    AreaSearchService areaSearchService;

    @Autowired
    AreaProjectPlanDescRepository areaProjectPlanDescRepository;

    @Autowired
    AreaProjectService areaProjectService;


    String objectName = "项目规划信息";


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
    public MyPage data(HttpServletRequest request, HttpSession session,
                       @RequestParam(value = "current", defaultValue = "0") int current,
                       @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                       @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);

        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortServiceWithSelectedIds(areaSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public ReturnObject save(Area area) {
//        area = areaService.save(area);
//        return super.save(objectName, area);
//    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Area> findAll() {
        return areaService.findAll();
    }


    @RequestMapping("/findById/{id}")
    public Area findById(@PathVariable("id") Long id) {
        return areaService.findById(id);
    }


    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        areaService.delete(id);
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
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response,
                            @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);

        List<Area> dataList = areaSearchService.findByConditions(param, paramsNum, selectedIds);
        areaService.setDataList(dataList);
        areaService.exportExcel(request, response, docName, titles, colNames);
    }


    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaService.selectAllIds();
    }


    /**
     * @return 根据位置查询项目总体布局描述信息
     */
    @ResponseBody
    @RequestMapping(value = "/layoutDesc/{locId}", method = RequestMethod.GET)
    public AreaProjectPlanDesc getLayoutDescByLocId(@PathVariable("locId") Long locId) {
        return areaProjectPlanDescRepository.findByLocation_IdAndStatusOrderByIdDesc(locId, "1");
    }


    /**
     * @return 根据位置查询项目总体布局描述信息
     */
    @ResponseBody
    @RequestMapping(value = "/planDesc/{locId}", method = RequestMethod.GET)
    public AreaProjectPlanDesc getPlanDescByLocId(@PathVariable("locId") Long locId) {
        return areaProjectPlanDescRepository.findByLocation_IdAndStatusOrderByIdDesc(locId, "1");
    }


    /**
     * @return 查询规划项目 按照位置级别对工程类型进行统计
     */
    @RequestMapping(value = "/findProjectPlanEneStat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> findProjectPlanEneStat(@RequestParam Long locLevel, @RequestParam Long locId) {
        return areaProjectService.findProjectPlanEneStat(locLevel, locId);
    }


    /**
     * @return 查询规划项目 按照位置级别 对行政区划统计
     */
    @RequestMapping(value = "/findProjectPlanStat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> findProjectPlanStat(@RequestParam Long locLevel, @RequestParam Long locId) {
        return areaProjectService.findProjectPlanStat(locLevel, locId);
    }




}
