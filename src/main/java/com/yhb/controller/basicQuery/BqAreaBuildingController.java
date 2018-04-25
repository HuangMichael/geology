package com.yhb.controller.basicQuery;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaBuilding.AreaBuilding;
import com.yhb.domain.areaBuilding.VAreaBuilding;
import com.yhb.service.building.AreaBuildingService;
import com.yhb.service.building.VAreaBuildingSearchService;
import com.yhb.service.building.VAreaBuildingService;
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
 * Created by Administrator on 2017/7/19.
 */
@Controller
@RequestMapping("/bqAreaBuilding")
public class BqAreaBuildingController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;
    @Autowired
    AreaBuildingService areaBuildingService;

    @Autowired
    VAreaBuildingService vAreaBuildingService;

    @Autowired
    VAreaBuildingSearchService vAreaBuildingSearchService;



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
    public MyPage data(HttpServletRequest request,HttpSession session,
                       @RequestParam(value = "current", defaultValue = "0") int current,
                       @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                       @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortServiceWithSelectedIds(vAreaBuildingSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }
    /**
     * @param request  请求
     * @param session
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     * @param selectedIds
     * @param sort
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);
        List<VAreaBuilding> dataList = vAreaBuildingSearchService.findByConditions(param, paramsNum,selectedIds);
        vAreaBuildingSearchService.setDataList(dataList);
        vAreaBuildingSearchService.exportExcel(request, response, docName, titles, colNames);
    }
    /**
     * @param id 对象id
     * @return 围垦信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public VAreaBuilding findById(@PathVariable("id") Long id) {
        return vAreaBuildingService.findById(id);
    }

    /**
     * @return 围垦信息列表
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<VAreaBuilding> findAll() {
        return vAreaBuildingService.findAll();
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vAreaBuildingService.selectAllIds();
    }
}
