package com.yhb.controller.systemManage;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.AreaProjectReport;
import com.yhb.domain.areaProject.VAreaProjectReport;
import com.yhb.service.areaProject.AreaProjectReportService;
import com.yhb.service.areaProject.VAreaProjectReportSearchService;
import com.yhb.service.areaProject.VAreaProjectReportService;
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
 * Created by Administrator on 2017/10/18.
 * 系统管理-基础数据管理-项目可行性研究报告
 */
@Controller
@RequestMapping("/sysMngProjectReport")
public class SysMngProjectReportController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;

    @Autowired
    AreaProjectReportService areaProjectReportService;

    @Autowired
    VAreaProjectReportService vAreaProjectReportService;

    @Autowired
    VAreaProjectReportSearchService vAreaProjectReportSearchService;


    String objectName = "项目可行性研究报告";

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
        return new PageUtils().searchBySortServiceWithSelectedIds(vAreaProjectReportSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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

        List<VAreaProjectReport> dataList = vAreaProjectReportSearchService.findByConditions(param, paramsNum, selectedIds);
        vAreaProjectReportSearchService.setDataList(dataList);
        vAreaProjectReportSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vAreaProjectReportService.selectAllIds();
    }

    /**
     * @param id
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param dykeLevel
     * @param designStandard
     * @param dykeLineSetting
     * @param dykeSectionalType
     * @param dykeTopHeight
     * @param dykeTopWidth
     * @param slopeRatio
     * @param investedSum
     * @param investedProvince
     * @param investedCity
     * @param investedSelf
     * @param status
     * @return 保存可行性研究报告
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "projectId") Long projectId,
                             @RequestParam(value = "beginDate", required = false) String beginDate,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "dykeLevel", required = false) String dykeLevel,
                             @RequestParam(value = "designStandard", required = false) String designStandard,
                             @RequestParam(value = "dykeLineSetting", required = false) String dykeLineSetting,
                             @RequestParam(value = "dykeSectionalType", required = false) String dykeSectionalType,
                             @RequestParam(value = "dykeTopHeight", required = false) String dykeTopHeight,
                             @RequestParam(value = "dykeTopWidth", required = false) String dykeTopWidth,
                             @RequestParam(value = "slopeRatio", required = false) String slopeRatio,
                             @RequestParam(value = "investedSum", required = false) Double investedSum,
                             @RequestParam(value = "investedProvince", required = false) Double investedProvince,
                             @RequestParam(value = "investedCity", required = false) Double investedCity,
                             @RequestParam(value = "investedSelf", required = false) Double investedSelf,
                             @RequestParam(value = "status") String status) {
        AreaProjectReport areaProjectReport = areaProjectReportService.save(id, projectId, beginDate, endDate, dykeLevel, designStandard,
                dykeLineSetting, dykeSectionalType, dykeTopHeight, dykeTopWidth, slopeRatio, investedSum, investedProvince, investedCity, investedSelf, status);
        return super.save(objectName, areaProjectReport);
    }

    /**
     * @param id 对象id
     * @return 根据id查询可行性研究报告视图信息
     */
    @RequestMapping("/findVById/{id}")
    @ResponseBody
    public VAreaProjectReport findVById(@PathVariable("id") Long id) {
        return vAreaProjectReportService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 根据id查询可行性研究报告信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public AreaProjectReport findById(@PathVariable("id") Long id) {
        return areaProjectReportService.findById(id);
    }

    /**
     * @return 查询所有的可行性研究报告
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<AreaProjectReport> findAll() {
        return areaProjectReportService.findAll();
    }

    /**
     * @return 查询所有的可行性研究报告视图
     */
    @RequestMapping("/findVAll")
    @ResponseBody
    public List<VAreaProjectReport> findVAll() {
        return vAreaProjectReportService.findAll();
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaProjectReportService.delete(id);
    }

    /**
     * @param projectReportIds
     * @return 对可行性研究报告列表进行批量审核，仅仅将未审核的数据的status由“0”改为“1”，已审核的就不再审核
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("projectReportIds") String projectReportIds) {
        List<AreaProjectReport> resultList = areaProjectReportService.authorizeInBatch(projectReportIds);
        int authedNum = resultList.size();
        int notAuthedNum = (projectReportIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "可行性研究报告信息:" + authedNum + "条审核成功，" + notAuthedNum + "条已审核！", "可行性研究报告信息:"+ notAuthedNum + "条已审核！");
    }
}
