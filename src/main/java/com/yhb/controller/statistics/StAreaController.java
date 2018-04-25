package com.yhb.controller.statistics;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.Area;
import com.yhb.domain.area.VArea;
import com.yhb.domain.areaBuilding.VAreaStatistics;
import com.yhb.service.area.AreaService;
import com.yhb.service.area.VAreaSearchService;
import com.yhb.service.area.VAreaService;
import com.yhb.service.building.VAreaBuildingService;
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
 * Created by huangbin on 2017/5/22 0004.
 * 统计分析报表-围垦区块
 */
@Controller
@RequestMapping("/stArea")
public class StAreaController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;

    @Autowired
    AreaService areaService;

    @Autowired
    VAreaService vAreaService;

    @Autowired
    VAreaSearchService vAreaSearchService;

    @Autowired
    VAreaBuildingService vAreaBuildingService;

    String objectName = "围垦区块信息";



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
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response,
                            @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);

        List<VArea> dataList = vAreaSearchService.findByConditions(param, paramsNum, selectedIds);
        vAreaSearchService.setDataList(dataList);
        vAreaSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vAreaService.selectAllIds();
    }


//    /**
//     * @param area
//     * @return 保存区块信息
//     */
//    @RequestMapping(value = "/save", method = RequestMethod.POST)
//    @ResponseBody
//    public ReturnObject save(Area area) {
//        areaService.save(area);
//        return super.save(objectName, area);
//    }

    /**
     * @return 查询所有的区块视图
     */
    @RequestMapping("/findVAll")
    @ResponseBody
    public List<VArea> findVAll() {
        return vAreaService.findAll();
    }

    /**
     * @return 查询所有的区块
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Area> findAll() {
        return areaService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块信息
     */
    @RequestMapping("/findVById/{id}")
    @ResponseBody
    public VArea findVById(@PathVariable("id") Long id) {
        return vAreaService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Area findById(@PathVariable("id") Long id) {
        return areaService.findById(id);
    }

    /**
     * @param id
     * @return 删除区块信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaService.delete(id);
    }

    /**
     * @return 根据条件复合查询已围垦的用地类型统计数据求和，并按照区块编号分组、排序
     */
    @RequestMapping(value = "/area/typeSizeGroupByAreaNo", method = RequestMethod.POST)
    @ResponseBody
    public List<VAreaStatistics> findAreaTypeSizeGroupByAreaNo(HttpSession session, @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        List<String> areaNoList = vAreaSearchService.getAreaNoListByConditions(searchPhrase, paramsNum);
        return vAreaBuildingService.findAreaTypeSizeGroupByAreaNoForAreaStat(areaNoList);
    }

    /**
     * @param id
     * @return 根据id查询该区块的各类型面积求和的值
     */
    @RequestMapping(value = "/area/typeSizeById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public VAreaStatistics findAreaTypeSizeTotalById(@PathVariable("id") Long id) {
        return vAreaBuildingService.findAreaTypeSizeTotalById(id);
    }
}
