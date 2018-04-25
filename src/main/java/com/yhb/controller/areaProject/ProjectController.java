package com.yhb.controller.areaProject;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.*;
import com.yhb.domain.areaProject.projectStat.*;
import com.yhb.service.areaProject.AreaProjectService;
import com.yhb.service.areaProject.VAreaProjectSearchService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import com.yhb.vo.project.Project4Sel;
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
@RequestMapping("/project")
public class ProjectController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;

    @Autowired
    VAreaProjectSearchService vAreaProjectSearchService;

    @Autowired
    AreaProjectService areaProjectService;

    String objectName = "项目信息";

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
        return new PageUtils().searchBySortServiceWithSelectedIds(vAreaProjectSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    /**
     * @param session
     * @param id
     * @param projectNo
     * @param projectName
     * @param areaId
     * @param cityId
     * @param districtId
     * @param beginYear
     * @param endYear
     * @param projectSize
     * @param mainPurpose
     * @param projectLeader
     * @param leaderContact
     * @param consUnit
     * @param monitorUnit
     * @param acceptUnit
     * @param budget
     * @param investedSum
     * @param taskProgress
     * @param landUsingTypeId
     * @param engineeringTypeId
     * @param status
     * @param buildStatus
     * @return 保存项目信息，同时保存当前用户的授权码
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(HttpSession session,
                             @RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "projectNo") String projectNo,
                             @RequestParam(value = "projectName") String projectName,
                             @RequestParam(value = "areaId", required = false) Long areaId,
                             @RequestParam(value = "cityId", required = false) Long cityId,
                             @RequestParam(value = "districtId", required = false) Long districtId,
                             @RequestParam(value = "beginYear", required = false) String beginYear,
                             @RequestParam(value = "endYear", required = false) String endYear,
                             @RequestParam(value = "projectSize", required = false) Double projectSize,
                             @RequestParam(value = "mainPurpose", required = false) String mainPurpose,
                             @RequestParam(value = "projectLeader", required = false) String projectLeader,
                             @RequestParam(value = "leaderContact", required = false) String leaderContact,
                             @RequestParam(value = "consUnit", required = false) String consUnit,
                             @RequestParam(value = "monitorUnit", required = false) String monitorUnit,
                             @RequestParam(value = "acceptUnit", required = false) String acceptUnit,
                             @RequestParam(value = "budget", required = false) Double budget,
                             @RequestParam(value = "investedSum", required = false) Double investedSum,
                             @RequestParam(value = "taskProgress", required = false) String taskProgress,
                             @RequestParam(value = "landUsingTypeId", required = false) Long landUsingTypeId,
                             @RequestParam(value = "engineeringTypeId", required = false) Long engineeringTypeId,
                             @RequestParam(value = "status") String status,
                             @RequestParam(value = "buildStatus", defaultValue = "0", required = false) String buildStatus,
                             @RequestParam(value = "important", defaultValue = "0") String important) {
        AreaProject areaProject = areaProjectService.save(session, id, projectNo, projectName, areaId, cityId, districtId,
                beginYear, endYear, projectSize, mainPurpose, projectLeader, leaderContact, consUnit, monitorUnit,
                acceptUnit, budget, investedSum, taskProgress, landUsingTypeId, engineeringTypeId, status, buildStatus, important);
        return super.save(objectName, areaProject);
    }


    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     * @param sort
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);

        List<VAreaProject> dataList = vAreaProjectSearchService.findByConditions(param, paramsNum, selectedIds);
        vAreaProjectSearchService.setDataList(dataList);
        vAreaProjectSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaProjectService.selectAllIds();
    }


    /**
     * @return 查询所有的项目信息
     */
    @ResponseBody
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<AreaProject> findAll() {
        return areaProjectService.findAll();
    }

    /**
     * @return 查询id查询项目信息
     */

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AreaProject findById(@PathVariable("id") Long id) {
        return areaProjectService.findById(id);
    }

    /**
     * @param id
     * @return 根据id删除项目信息
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaProjectService.delete(id);
    }

    /**
     * @param projectNo
     * @return 根据项目编号查询
     */
    @RequestMapping(value = "/findByProjectNo", method = RequestMethod.POST)
    @ResponseBody
    public AreaProject findByProjectNo(@RequestParam(value = "projectNo") String projectNo) {
        return areaProjectService.findByProjectNo(projectNo);
    }

    /**
     * @param id
     * @param projectNo
     * @return 编辑项目信息时，根据前端传过来的用户输入的新项目编号和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    @RequestMapping(value = "/findProjectDuplicateByNoAndId", method = RequestMethod.POST)
    @ResponseBody
    public AreaProject findProjectDuplicateByNoAndId(@RequestParam("id") Long id, @RequestParam("projectNo") String projectNo) {
        return areaProjectService.findProjectDuplicateByNoAndId(id, projectNo);
    }

    /**
     * @param projectName
     * @return 根据项目名称查询
     */
    @RequestMapping(value = "/findByProjectName", method = RequestMethod.POST)
    @ResponseBody
    public AreaProject findByProjectName(@RequestParam(value = "projectName") String projectName) {
        return areaProjectService.findByProjectName(projectName);
    }

    /**
     * @param id
     * @param projectName
     * @return 编辑项目信息时，根据前端传过来的用户输入的新项目名称和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    @RequestMapping(value = "/findProjectDuplicateByNameAndId", method = RequestMethod.POST)
    @ResponseBody
    public AreaProject findProjectDuplicateByNameAndId(@RequestParam("id") Long id, @RequestParam("projectName") String projectName) {
        return areaProjectService.findProjectDuplicateByNameAndId(id, projectName);
    }

    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "project/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return areaProjectService.findMaxId();
    }


    /**
     * @param projectIds
     * @return 对项目列表进行批量审核，将status由“0”改为“1”
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("projectIds") String projectIds) {
        List<AreaProject> resultList = areaProjectService.authorizeInBatch(projectIds);
        int authedNum = resultList.size();
        int notAuthedNum = (projectIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "项目信息:" + resultList.size() + "条审核成功，" + notAuthedNum + "条已审核！", "项目信息审核失败！");
    }


    /**
     * @return 根据项目建设状态查询项目信息
     */
    @RequestMapping(value = "/findByBuildStatus/{buildStatus}", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaProject> findByBuildStatus(@PathVariable("buildStatus") String findByBuildStatus) {
        return areaProjectService.findByBuildStatus(findByBuildStatus);
    }


    /**
     * @return 查询所有在建或者已建项目，按照项目重要性排序
     */
    @RequestMapping("/findAll4Sel")
    @ResponseBody
    public List<Project4Sel> findAll4Sel() {
        return areaProjectService.findAll4Sel();
    }

    /**
     * @return 根据位置id查询所有在建或者已建项目，按照项目重要性排序
     */
    @RequestMapping("/findAll4SelByLocId/{locId}")
    @ResponseBody
    public List<Project4Sel> findAll4SelByLocId(@PathVariable("locId") Long locId) {
        return areaProjectService.findAll4SelByLocId(locId);
    }

    /**
     * @return 查询所有规划项目，按照项目重要性排序
     */
    @RequestMapping("/findPlanProjectAll4Sel")
    @ResponseBody
    public List<Project4Sel> findPlanProjectAll4Sel() {
        return areaProjectService.findPlanProjectAll4Sel();
    }

    /**
     * @return 根据位置id查询所有规划项目，按照项目重要性排序
     */
    @RequestMapping("/findPlanProjectAll4SelByLocId/{locId}")
    @ResponseBody
    public List<Project4Sel> findPlanProjectAll4SelByLocId(@PathVariable("locId") Long locId) {
        return areaProjectService.findPlanProjectAll4SelByLocId(locId);
    }


    /**
     * @return 根据项目建设状态查询项目信息
     */
    @RequestMapping(value = "/findMyProjects", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaProject> findMyProjects(HttpSession session) {
        return areaProjectService.findMyProjects(session);
    }

    /**
     * @return 根据授权码和审核状态查询项目信息
     */
    @RequestMapping(value = "/findVByAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VAreaProject> findVByAuthKeyAndStatus(HttpSession session, @RequestParam("authStatus") String authStatus) {
        return vAreaProjectSearchService.findByAuthKeyAndStatus(session, authStatus);
    }

    /**
     * @return 根据 授权码、市、县区、审核状态 查询项目信息
     */
    @RequestMapping(value = "/findVByAuthKeyAndCityNameAndDistrictNameAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VAreaProject> findVByAuthKeyAndCityNameAndDistrictNameAndStatus(HttpSession session, @RequestParam("cityName") String cityName,
                                                                                @RequestParam("districtName") String districtName, @RequestParam("authStatus") String authStatus) {
        return vAreaProjectSearchService.findByAuthKeyAndCityNameAndDistrictNameAndStatus(session, cityName, districtName, authStatus);
    }

    /**
     * @return 地图显示项目的相册
     */
    @RequestMapping(value = "/showGisGallery", method = RequestMethod.GET)
    public String showGisGallery() {
        return "projectTotalLayout/projectGallery";
    }


    /**
     * @return 查询项目 按照位置级别对工程类型进行统计
     */
    @RequestMapping(value = "/findProjectEneStat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> findProjectEneStat(@RequestParam Long locLevel, @RequestParam Long locId) {
        return areaProjectService.findProjectProEneStat(locLevel, locId);
    }


    /**
     * @return 查询项目 按照位置级别 对行政区划统计
     */
    @RequestMapping(value = "/findProjectStat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> findProjectStat(@RequestParam Long locLevel, @RequestParam Long locId) {
        return areaProjectService.findProjectProStat(locLevel, locId);
    }


}
