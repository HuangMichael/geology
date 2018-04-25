package com.yhb.controller.systemManage;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaPlan.ThirteenFivePlan;
import com.yhb.domain.areaPlan.VThirteenFivePlan;
import com.yhb.service.areaPlan.ThirteenFivePlanService;
import com.yhb.service.areaPlan.VThirteenFivePlanSearchService;
import com.yhb.service.areaPlan.VThirteenFivePlanService;
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
 * Created by Administrator on 2017/7/25.
 */

@Controller
@RequestMapping("/sysMngThirteenFivePlan")
public class SysMngThirteenFivePlanController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;
    @Autowired
    ThirteenFivePlanService thirteenFivePlanService;

    @Autowired
    VThirteenFivePlanService vThirteenFivePlanService;

    @Autowired
    VThirteenFivePlanSearchService vThirteenFivePlanSearchService;

    String objectName = "十三五规划信息";


    /**
     * 分页查询
     *
     * @param request
     * @param session
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
        return new PageUtils().searchBySortServiceWithSelectedIds(vThirteenFivePlanSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    /**
     * @param request     请求
     * @param session
     * @param response    响应
     * @param param       查询关键字
     * @param docName     文档名称
     * @param titles      标题集合
     * @param colNames    列名称
     * @param selectedIds 用户选择的记录id列表
     * @param sort
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);
        List<VThirteenFivePlan> dataList = vThirteenFivePlanSearchService.findByConditions(param, paramsNum, selectedIds);
        vThirteenFivePlanSearchService.setDataList(dataList);
        vThirteenFivePlanSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vThirteenFivePlanService.selectAllIds();
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
     * @return 保存十三五规划信息，同时保存当前用户的授权码
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
        ThirteenFivePlan thirteenFivePlan1 = thirteenFivePlanService.save(session, id, areaId, planDesc, cityId, districtId, beginYear, endYear, buildSize,
                nyBuildSize, jsBuildSize, stBuildSize, reportSize, status, memo);
        return super.save(objectName, thirteenFivePlan1);
    }


    /**
     * @return 查询所有的十三五规划视图信息
     */
    @RequestMapping("/findVAll")
    @ResponseBody
    public List<VThirteenFivePlan> findVAll() {
        return vThirteenFivePlanService.findAll();
    }

    /**
     * @return 查询所有的十三五规划信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<ThirteenFivePlan> findAll() {
        return thirteenFivePlanService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询十三五规划视图信息
     */
    @RequestMapping("/findVById/{id}")
    @ResponseBody
    public VThirteenFivePlan findVById(@PathVariable("id") Long id) {
        return vThirteenFivePlanService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 根据id查询十三五规划信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public ThirteenFivePlan findById(@PathVariable("id") Long id) {
        return thirteenFivePlanService.findById(id);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return thirteenFivePlanService.delete(id);
    }

    /**
     * @param thirteenFivePlanIds
     * @return 对十三五规划列表进行批量审核，status由“0”改为“1”
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("thirteenFivePlanIds") String thirteenFivePlanIds) {
        List<ThirteenFivePlan> resultList = thirteenFivePlanService.authorizeInBatch(thirteenFivePlanIds);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), resultList.size() + "个十三五规划信息审核成功！", "十三五规划信息审核失败！");
    }
}
