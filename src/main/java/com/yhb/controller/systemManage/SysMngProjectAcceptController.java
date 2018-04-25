package com.yhb.controller.systemManage;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.AreaProjectAccept;
import com.yhb.domain.areaProject.VAreaProjectAccept;
import com.yhb.service.areaProject.AreaProjectAcceptService;
import com.yhb.service.areaProject.VAreaProjectAcceptSearchService;
import com.yhb.service.areaProject.VAreaProjectAcceptService;
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
 * Created by Administrator on 2017/10/20.
 * 系统管理-基础数据管理-项目验收基本信息
 */
@Controller
@RequestMapping("/sysMngProjectAccept")
public class SysMngProjectAcceptController extends BaseController {

    //复合查询条件参数个数
    private final int paramsNum = 8;

    @Autowired
    AreaProjectAcceptService areaProjectAcceptService;

    @Autowired
    VAreaProjectAcceptService vAreaProjectAcceptService;

    @Autowired
    VAreaProjectAcceptSearchService vAreaProjectAcceptSearchService;

    String objectName = "项目验收基本信息";



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
        return new PageUtils().searchBySortServiceWithSelectedIds(vAreaProjectAcceptSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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

        List<VAreaProjectAccept> dataList = vAreaProjectAcceptSearchService.findByConditions(param, paramsNum, selectedIds);
        vAreaProjectAcceptSearchService.setDataList(dataList);
        vAreaProjectAcceptSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vAreaProjectAcceptSearchService.selectAllIds();
    }

//    @ResponseBody
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    public ReturnObject save(HttpSession session, @RequestParam(value = "id", required = false) Long id,
//                             @RequestParam(value = "projectId") Long projectId,
//                             @RequestParam(value = "beginDate") String beginDate,
//                             @RequestParam(value = "endDate") String endDate,
//                             @RequestParam(value = "mainPurpose", required = false) String mainPurpose,
//                             @RequestParam(value = "landUsingTypeId", required = false) Long landUsingTypeId,
//                             @RequestParam(value = "engineeringTypeId", required = false) Long engineeringTypeId,
//                             @RequestParam(value = "budget", required = false) Double budget,
//                             @RequestParam(value = "investedSum", required = false) Double investedSum,
//                             @RequestParam(value = "consUnit", required = false) String consUnit,
//                             @RequestParam(value = "monitorUnit", required = false) String monitorUnit,
//                             @RequestParam(value = "acceptUnit", required = false) String acceptUnit,
//                             @RequestParam(value = "projectLeader", required = false) String projectLeader,
//                             @RequestParam(value = "leaderContact", required = false) String leaderContact,
//                             @RequestParam(value = "taskProgress", required = false) String taskProgress,
//                             @RequestParam(value = "expert", required = false) String expert,
//                             @RequestParam(value = "conclusion", required = false) String conclusion,
//                             @RequestParam(value = "status") String status) {
//        AreaProjectAccept areaProjectAccept = areaProjectAcceptService.save(session, id, projectId, beginDate, endDate, mainPurpose,
//                landUsingTypeId, engineeringTypeId, budget, investedSum, consUnit, monitorUnit, acceptUnit, projectLeader,
//                leaderContact, taskProgress, expert, conclusion, status);
//        return super.save(objectName, areaProjectAccept);
//    }

    /**
     * @param id
     * @param projectId
     * @param expert
     * @param conclusion
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "projectId") Long projectId,
                             @RequestParam(value = "expert", required = false) String expert,
                             @RequestParam(value = "conclusion", required = false) String conclusion,
                             @RequestParam(value = "status") String status) {
        AreaProjectAccept areaProjectAccept = areaProjectAcceptService.save(id, projectId, expert, conclusion, status);
        return super.save(objectName, areaProjectAccept);
    }

    /**
     * @param id 对象id
     * @return 根据id查询项目验收基本信息视图信息
     */
    @RequestMapping("/findVById/{id}")
    @ResponseBody
    public VAreaProjectAccept findVById(@PathVariable("id") Long id) {
        return vAreaProjectAcceptService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 根据id查询项目验收基本信息信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public AreaProjectAccept findById(@PathVariable("id") Long id) {
        return areaProjectAcceptService.findById(id);
    }

    /**
     * @return 查询所有的项目验收基本信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<AreaProjectAccept> findAll() {
        return areaProjectAcceptService.findAll();
    }

    /**
     * @return 查询所有的项目验收基本信息视图
     */
    @RequestMapping("/findVAll")
    @ResponseBody
    public List<VAreaProjectAccept> findVAll() {
        return vAreaProjectAcceptService.findAll();
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaProjectAcceptService.delete(id);
    }

    /**
     * @param projectAcceptIds
     * @return 对项目验收基本信息列表进行批量审核，仅仅将未审核的数据的status由“0”改为“1”，已审核的就不再审核
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("projectAcceptIds") String projectAcceptIds) {
        List<AreaProjectAccept> resultList = areaProjectAcceptService.authorizeInBatch(projectAcceptIds);
        int authedNum = resultList.size();
        int notAuthedNum = (projectAcceptIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "项目验收基本信息:" + authedNum + "条审核成功，" + notAuthedNum + "条已审核！", "项目验收基本信息:"+ notAuthedNum + "条已审核！");
    }
}
