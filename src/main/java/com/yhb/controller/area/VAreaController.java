package com.yhb.controller.area;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.AreaTree;
import com.yhb.domain.area.VArea;
import com.yhb.service.area.VAreaSearchService;
import com.yhb.service.area.VAreaService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/vArea")
public class VAreaController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;
    @Autowired
    VAreaService vAreaService;

    @Autowired
    VAreaSearchService vAreaSearchService;

    String objectName = "围垦区块信息";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(vAreaSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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

        List<VArea> dataList = vAreaSearchService.findByConditions(param, paramsNum, selectedIds);
        vAreaService.setDataList(dataList);
        vAreaService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vAreaService.selectAllIds();
    }



//
//    /**
//     * @return 查询区块树, 而且根据授权码和审核状态查询
//     */
//    @ResponseBody
//    @RequestMapping(value = "/findByLocationAuthKeyAndStatus", method = RequestMethod.POST)
//    public List<VArea> findByLocationAuthKeyAndStatus(HttpSession session, @RequestParam("authStatus") String authStatus) {
//        return areaService.findAreaTreeByAuthKeyAndStatus(session, authStatus);
//    }



}
