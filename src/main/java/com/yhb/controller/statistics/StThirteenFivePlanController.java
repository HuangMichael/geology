package com.yhb.controller.statistics;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaPlan.ThirteenFivePlan;
import com.yhb.domain.areaPlan.VThirteenFivePlan;
import com.yhb.service.areaPlan.ThirteenFivePlanService;
import com.yhb.service.areaPlan.VThirteenFivePlanSearchService;
import com.yhb.service.areaPlan.VThirteenFivePlanService;
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
 * 统计分析报表-十三五规划
 */
@Controller
@RequestMapping("/stThirteenFivePlan")
public class StThirteenFivePlanController extends BaseController {
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
        List<VThirteenFivePlan> dataList = vThirteenFivePlanSearchService.findByConditions(param, paramsNum,selectedIds);
        vThirteenFivePlanSearchService.setDataList(dataList);
        vThirteenFivePlanSearchService.exportExcel(request, response, docName, titles, colNames);
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
     * @return 查询所有的十三五规划
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<ThirteenFivePlan> findAll() {
        return thirteenFivePlanService.findAll();
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
     * @param id 对象id
     * @return 根据id查询十三五规划视图信息
     */
    @RequestMapping("/findVById/{id}")
    @ResponseBody
    public VThirteenFivePlan findVById(@PathVariable("id") Long id) {
        return vThirteenFivePlanService.findById(id);
    }

    /**
     * @return 根据areaId查询该区块的十三五规划
     */
    @RequestMapping(value = "/findByAreaId/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public ThirteenFivePlan findByAreaId(@PathVariable("areaId") Long areaId) {
        return thirteenFivePlanService.findByAreaId(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    @RequestMapping(value = "/area/areaTypeSize/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> findAreaTypeSizeByAreaId(@PathVariable("areaId") Long areaId) {
        return thirteenFivePlanService.findAreaTypeSizeByAreaId(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块id查询该区块的规划的用地类型统计数据求和
     */
    @RequestMapping(value = "/area/areaTypeSizeTotal/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findAreaTypeSizeTotalByAreaId(@PathVariable("areaId") Long areaId) {
        return thirteenFivePlanService.findAreaTypeSizeTotalByAreaId(areaId);
    }


//    /**
//     * @return 查询某区块的规划的用地类型统计数据求和，并按照区块编号分组
//     */
//    @RequestMapping(value = "/area/typeSizeGroupByAreaNo", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Object> findAreaTypeSizeGroupByAreaNo() {
//        return vThirteenFivePlanService.findAreaTypeSizeGroupByAreaNo();
//    }

    /**
     * @return 查询符合查询条件的规划的用地类型统计数据求和，并按照区块编号分组
     */
    @RequestMapping(value = "/area/typeSizeGroupByAreaNo", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> findAreaTypeSizeGroupByAreaNo(HttpSession session,@RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        List<Long> idList = vThirteenFivePlanSearchService.getIdListByConditions(searchPhrase, paramsNum);
        return vThirteenFivePlanService.findAreaTypeSizeGroupByAreaNo(idList);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vThirteenFivePlanService.selectAllIds();
    }
}
