package com.yhb.controller.areaPlan;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaPlan.*;
import com.yhb.service.area.AreaSearchService;
import com.yhb.service.area.AreaService;
import com.yhb.service.areaPlan.TotalPlanService;
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
@RequestMapping("/totalPlan")
public class TotalPlanController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 3;
    @Autowired
    AreaService areaService;
    @Autowired
    AreaSearchService areaSearchService;

    @Autowired
    TotalPlanService totalPlanService;

    String objectName = "总体规划信息";

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
        return "totalPlan/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return totalPlanService.findMaxId();
    }


    //根据位置id查询该位置的总体规划描述


    /**
     * @return 根据位置查询该位置的总体规划描述
     */
    @RequestMapping(value = "/findPlanDesc/{locId}", method = RequestMethod.GET)
    @ResponseBody
    public TotalPlanDesc findTotalPlanDescByLocId(@PathVariable("locId") Long locId) {
        return totalPlanService.findTotalPlanDescByLocId(locId);
    }

    /**
     * @return 省级总体规划统计数据 下钻到市
     */
    @RequestMapping(value = "/totalPlanProv", method = RequestMethod.GET)
    @ResponseBody
    public List<VProvPlan> findProvTotalPlanStat() {
        return totalPlanService.findProvTotalPlanStat();
    }


    /**
     * @return 省级总体规划统计数据 下钻到市
     */
    @RequestMapping(value = "/totalPlanCity/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<VCityPlan> findCityTotalPlanStat(@PathVariable("parentId") Long parentId) {
        return totalPlanService.findCityTotalPlanStat(parentId);
    }


    @RequestMapping(value = "/oneKeyImport", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject oneKeyImport() {
        totalPlanService.oneKeyImport(this.getServiceTableName());
        log.info(this.getServiceTableName());
        return this.getCommonDataService().getReturnType(true, "数据一键导入成功！", "数据一键导入失败");

    }

    @RequestMapping(value = "/oneKeyExport", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject oneKeyExport(HttpServletRequest request, HttpServletResponse response) {
        //根据业务表名称  查询表的id
        Boolean result = totalPlanService.oneKeyExport(this.getServiceTableName(), request, response);
        log.info(this.getServiceTableName());
        return this.getCommonDataService().getReturnType(result, "数据一键导出成功！", "数据一键导出失败");
    }

    /**
     * @param areaId
     * @return 查询区块的总体规划信息
     */
    @RequestMapping(value = "/findByAreaId/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public VTotalPlanArea findTotalPlanByAreaId(@PathVariable("areaId") Long areaId) {
        return totalPlanService.findTotalPlanByAreaId(areaId);

    }
}


