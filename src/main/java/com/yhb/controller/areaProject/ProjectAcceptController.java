package com.yhb.controller.areaProject;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.AreaProjectAccept;
import com.yhb.service.areaProject.AreaProjectAcceptSearchService;
import com.yhb.service.areaProject.AreaProjectAcceptService;
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
 * Created by huangbin on 2017/5/20 0004.
 * 项目验收信息
 */
@Controller
@RequestMapping("/projectAccept")
public class ProjectAcceptController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;
    @Autowired
    AreaProjectAcceptService areaProjectAcceptService;

    @Autowired
    AreaProjectAcceptSearchService areaProjectAcceptSearchService;



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
    public MyPage data(HttpServletRequest request,HttpSession session,
                       @RequestParam(value = "current", defaultValue = "0") int current,
                       @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                       @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortServiceWithSelectedIds(areaProjectAcceptSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    /**
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<AreaProjectAccept> findAll() {
        return areaProjectAcceptService.findAll();
    }


    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    public AreaProjectAccept findById(@PathVariable("id") Long id) {
        return areaProjectAcceptService.findById(id);
    }


    /**
     * @param id
     */
    @RequestMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        areaProjectAcceptService.delete(id);
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
                            @RequestParam(value = "selectedIds") List<Long> selectedIds,@RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);

        List<AreaProjectAccept> dataList = areaProjectAcceptSearchService.findByConditions(param, paramsNum,selectedIds);
        areaProjectAcceptSearchService.setDataList(dataList);
        areaProjectAcceptSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaProjectAcceptService.selectAllIds();
    }

}
