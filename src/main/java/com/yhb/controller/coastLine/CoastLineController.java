package com.yhb.controller.coastLine;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.coastline.CoastLine;
import com.yhb.service.coastLine.CoastLineSearchService;
import com.yhb.service.coastLine.CoastLineService;
import com.yhb.utils.PageUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * Created by huangbin on 2017/5/20 0004.
 * 海岸线控制器类
 */
@Controller
@RequestMapping("/coastLine")
public class CoastLineController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 2;

    @Autowired
    CoastLineSearchService coastLineSearchService;
    @Autowired
    CoastLineService coastLineService;

    String objectName = "海岸线信息";


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
    public MyPage data(HttpServletRequest request,
                       @RequestParam(value = "current", defaultValue = "0") int current,
                       @RequestParam(value = "rowCount", defaultValue = "10") Long rowCount,
                       @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Pageable pageable = new PageRequest(current - 1, rowCount.intValue(), super.getSort(parameterMap));
        return new PageUtils().searchBySortServiceWithSelectedIds(coastLineSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "lineNo") String lineNo,
                             @RequestParam(value = "lineName") String lineName,
                             @RequestParam(value = "locationId", required = false) Long locationId,
                             @RequestParam(value = "lineLength", required = false) Double lineLength,
                             @RequestParam(value = "coastLineTypeId", required = false) Long coastLineTypeId,
                             @RequestParam(value = "startPoint", required = false) String startPoint,
                             @RequestParam(value = "endPoint", required = false) String endPoint,
                             @RequestParam(value = "status") String status) {
        CoastLine coastLine = coastLineService.save(id, lineNo, lineName, locationId, lineLength, coastLineTypeId,
                startPoint, endPoint, status);
        return super.save(objectName, coastLine);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<CoastLine> findAll() {
        return coastLineService.findAll();
    }


    @RequestMapping("/findById/{id}")
    @ResponseBody
    public CoastLine findById(@PathVariable("id") Long id) {
        return coastLineService.findById(id);
    }


    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return coastLineService.delete(id);
    }


//    /**
//     * @param areaId 围垦区块id
//     * @return 根据围垦区块id查询区块边界信息
//     */
//    @RequestMapping("/findCoastLineBoundary/{areaId}")
//    @ResponseBody
//    public List<Object> findCoastLineBoundaryByCoastLineId(@PathVariable("areaId") Long areaId) {
//        return areaBoundaryService.findCoastLineBoundaryByCoastLineId(areaId);
//    }


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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        List<CoastLine> dataList = coastLineSearchService.findByConditions(param, paramsNum, selectedIds);
        coastLineService.setDataList(dataList);
        coastLineService.exportExcel(request, response, docName, titles, colNames);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return coastLineService.selectAllIds();
    }



    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "coastLine/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return coastLineService.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 对海岸线列表进行批量审核，仅仅将未审核的数据的status由“0”改为“1”，已审核的就不再审核
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("selectedIds") String selectedIds) {
        List<CoastLine> resultList = coastLineService.authorizeInBatch(selectedIds);
        int authedNum = resultList.size();
        int notAuthedNum = (selectedIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "海岸线信息:" + authedNum + "条审核成功，" + notAuthedNum + "条已审核！", "海岸线信息:"+ notAuthedNum + "条已审核！");
    }
}
