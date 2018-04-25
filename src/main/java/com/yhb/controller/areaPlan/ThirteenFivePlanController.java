package com.yhb.controller.areaPlan;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaPlan.*;
import com.yhb.service.area.AreaSearchService;
import com.yhb.service.area.AreaService;
import com.yhb.service.areaPlan.ThirteenFivePlanService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
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
@RequestMapping("/thirteenFivePlan")
public class ThirteenFivePlanController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 3;
    @Autowired
    AreaService areaService;
    @Autowired
    AreaSearchService areaSearchService;


    @Autowired
    ThirteenFivePlanService thirteenFivePlanService;

    String objectName = "十三五规划信息";


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
     * @return 绘制地图
     */
    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "thirteenFivePlan/create";
    }

    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return thirteenFivePlanService.findMaxId();
    }


    /**
     * @return 根据位置查询该位置的总体规划描述
     */
    @RequestMapping(value = "/findPlanDesc/{locId}", method = RequestMethod.GET)
    @ResponseBody
    public ThirteenFivePlanDesc findTotalPlanDescByLocId(@PathVariable("locId") Long locId) {
        return thirteenFivePlanService.find135PlanDescByLocId(locId);
    }


    /**
     * @return 省级十三五规划统计数据 下钻到市
     */
    @RequestMapping(value = "/135PlanProv", method = RequestMethod.GET)
    @ResponseBody
    public List<VProv135Plan> findProv135PlanStat() {
        return thirteenFivePlanService.findProv135PlanStat();
    }


    /**
     * @return 省级十三五规划统计数据 下钻到市
     */
    @RequestMapping(value = "/135PlanCity/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<VCity135Plan> findCity135PlanStat(@PathVariable("parentId") Long parentId) {
        return thirteenFivePlanService.findCity135PlanStat(parentId);
    }


    /**
     * @param areaId 区块id
     * @return 根据区块的id 查询十三五规划信息
     */
    @RequestMapping(value = "/findByAreaId/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public V135PlanArea find135PlanByAreaId(@PathVariable("areaId") Long areaId) {
        return thirteenFivePlanService.find135PlanByAreaId(areaId);

    }
}
