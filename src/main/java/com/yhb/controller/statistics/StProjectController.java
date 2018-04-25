package com.yhb.controller.statistics;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.AreaProject;
import com.yhb.domain.areaProject.VAreaProject;
import com.yhb.service.areaProject.AreaProjectSearchService;
import com.yhb.service.areaProject.AreaProjectService;
import com.yhb.service.areaProject.VAreaProjectSearchService;
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
 * Created by huangbin on 2017/5/22 0004.
 * 统计分析报表-项目统计分析
 */
@Controller
@RequestMapping("/stProject")
public class StProjectController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;
    @Autowired
    AreaProjectService areaProjectService;

    @Autowired
    AreaProjectSearchService areaProjectSearchService;

    @Autowired
    VAreaProjectSearchService vAreaProjectSearchService;

    String objectName = "统计分析报表-项目信息";
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

        List<VAreaProject> dataList = vAreaProjectSearchService.findByConditions(param, paramsNum,selectedIds);
        vAreaProjectSearchService.setDataList(dataList);
        vAreaProjectSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @param id 项目id
     * @return 项目信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AreaProject findById(@PathVariable("id") Long id) {
        return areaProjectService.findById(id);
    }

    /**
     * @return 查询全部的项目信息
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaProject> findAll() {
        return areaProjectService.findAll();
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaProjectService.selectAllIds();
    }

    /**
     * @return 获取所有项目的 预算、已投资额 信息
     */
    @ResponseBody
    @RequestMapping(value = "/getAllProjectFund", method = RequestMethod.GET)
    public List<Object> getAllProjectFund() {
        return areaProjectService.getAllProjectFund();
    }

    /**
     * @return 获取符合查询条件的项目的 预算、已投资额 信息
     */
    @ResponseBody
    @RequestMapping(value = "/getProjectFundByIds", method = RequestMethod.GET)
    public List<Object> getProjectFundByIds(HttpSession session,@RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        List<Long> idList = vAreaProjectSearchService.getIdListByConditions(searchPhrase, paramsNum);
        return areaProjectService.getProjectFundByIds(idList);
    }
}
