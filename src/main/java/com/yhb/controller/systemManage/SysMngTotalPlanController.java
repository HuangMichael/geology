package com.yhb.controller.systemManage;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaPlan.TotalPlan;
import com.yhb.domain.areaPlan.VTotalPlan;
import com.yhb.service.areaPlan.TotalPlanService;
import com.yhb.service.areaPlan.VTotalPlanSearchService;
import com.yhb.service.areaPlan.VTotalPlanService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.annotation.DataAttr;
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
@RequestMapping("/sysMngTotalPlan")
public class SysMngTotalPlanController extends BaseController {

    @Autowired
    VTotalPlanService vTotalPlanService;

    @Autowired
    TotalPlanService totalPlanService;

    @Autowired
    VTotalPlanSearchService vTotalPlanSearchService;


    int paramsNum = 8;


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
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);
        List<VTotalPlan> dataList = vTotalPlanSearchService.findByConditions(param, paramsNum, selectedIds);
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
     * @param id
     * @param areaId
     * @param planDesc
     * @param cityId
     * @param districtId
     * @param beginYear
     * @param endYear
     * @param buildSize
     * @param nyBuildSize
     * @param jsBuildSize
     * @param stBuildSize
     * @param reportSize
     * @param status
     * @return 保存总体规划，同时保存当前用户的授权码
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnObject save(HttpSession session,
                             @RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "areaId") Long areaId,
                             @RequestParam(value = "planDesc", required = false) String planDesc,
                             @RequestParam(value = "cityId", required = false) Long cityId,
                             @RequestParam(value = "districtId", required = false) Long districtId,
                             @RequestParam(value = "beginYear", required = false) String beginYear,
                             @RequestParam(value = "endYear", required = false) String endYear,
                             @RequestParam(value = "buildSize", required = false) Double buildSize,
                             @RequestParam(value = "nyBuildSize", required = false) Double nyBuildSize,
                             @RequestParam(value = "jsBuildSize", required = false) Double jsBuildSize,
                             @RequestParam(value = "stBuildSize", required = false) Double stBuildSize,
                             @RequestParam(value = "reportSize", required = false) Double reportSize,
                             @RequestParam(value = "status") String status,
                             @RequestParam(value = "memo", required = false) String memo) {
        TotalPlan totalPlan1 = totalPlanService.save(session, id, areaId, planDesc, cityId, districtId, beginYear, endYear, buildSize,
                nyBuildSize, jsBuildSize, stBuildSize, reportSize, status, memo);
        return super.save(objectName, totalPlan1);
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
     * @param id 对象id
     * @return 根据id查询总体规划信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public TotalPlan findById(@PathVariable("id") Long id) {
        return totalPlanService.findById(id);
    }

    /**
     * @return 查询所有的总体规划
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TotalPlan> findAll() {
        return totalPlanService.findAll();
    }

    /**
     * @return 查询所有的总体规划视图
     */
    @RequestMapping("/findVAll")
    @ResponseBody
    public List<VTotalPlan> findVAll() {
        return vTotalPlanService.findAll();
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

    /**
     * @param totalPlanIds
     * @return 对总体规划列表进行批量审核，status由“0”改为“1”
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("totalPlanIds") String totalPlanIds) {
        List<TotalPlan> resultList = totalPlanService.authorizeInBatch(totalPlanIds);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), resultList.size() + "个总体规划信息审核成功！", "总体规划信息审核失败！");
    }
}