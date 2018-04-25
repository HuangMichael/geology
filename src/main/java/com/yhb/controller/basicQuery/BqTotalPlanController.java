package com.yhb.controller.basicQuery;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaPlan.TotalPlan;
import com.yhb.domain.areaPlan.VTotalPlan;
import com.yhb.service.areaPlan.TotalPlanSearchService;
import com.yhb.service.areaPlan.TotalPlanService;
import com.yhb.service.areaPlan.VTotalPlanSearchService;
import com.yhb.service.areaPlan.VTotalPlanService;
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
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/bqTotalPlan")
public class BqTotalPlanController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;
    @Autowired
    VTotalPlanService vTotalPlanService;

    @Autowired
    TotalPlanService totalPlanService;

    @Autowired
    VTotalPlanSearchService vTotalPlanSearchService;

    String objectName = "基础数据查询-总体规划信息";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(vTotalPlanSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
    public void exportExcel(HttpServletRequest request,HttpSession session, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);
        List<VTotalPlan> dataList = vTotalPlanSearchService.findByConditions(param, paramsNum,selectedIds);
        vTotalPlanSearchService.setDataList(dataList);
        vTotalPlanSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vTotalPlanService.selectAllIds();
    }


    /**
     * @param id 对象id
     * @return 根据id查询总体规划信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public TotalPlan findById(@PathVariable("id") Long id) {
        return totalPlanService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 根据id查询总体规划视图信息
     */
    @RequestMapping("/findVById/{id}")
    @ResponseBody
    public VTotalPlan findVById(@PathVariable("id") Long id) {
        return vTotalPlanService.findById(id);
    }

    /**
     * @return 查询所有的总体规划视图信息
     */
    @RequestMapping(value = "/findVAll", method = RequestMethod.GET)
    @ResponseBody
    public List<VTotalPlan> findVAll() {
        return vTotalPlanService.findAll();
    }


    /**
     * @return 查询所有的总体规划信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<TotalPlan> findAll() {
        return totalPlanService.findAll();
    }

    /**
     * @return 根据areaId查询所有总体规划
     */
    @RequestMapping(value = "/findByAreaId/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public List<TotalPlan> findByAreaId(@PathVariable("areaId") Long areaId) {
        return totalPlanService.findByAreaId(areaId);
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return totalPlanService.delete(id);
    }
}