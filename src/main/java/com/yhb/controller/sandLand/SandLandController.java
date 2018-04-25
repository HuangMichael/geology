package com.yhb.controller.sandLand;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.sandLand.SandLand;
import com.yhb.service.sandLand.SandLandSearchService;
import com.yhb.service.sandLand.SandLandService;
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
 * Created by Administrator on 2017/6/19.
 */
@Controller
@RequestMapping("/sandLand")
public class SandLandController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 2;

    @Autowired
    SandLandSearchService sandLandSearchService;

    @Autowired
    SandLandService sandLandService;

    String objectName = "沿海沙洲信息";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(sandLandSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<SandLand> findAll() {
        return sandLandService.findAll();
    }


    @RequestMapping("/findById/{id}")
    @ResponseBody
    public SandLand findById(@PathVariable("id") Long id) {
        return sandLandService.findById(id);
    }

    /**
     * @param id
     * @param position
     * @param locationId
     * @param length
     * @param width
     * @return 保存沿海沙洲信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "sandNo") String sandNo,
                             @RequestParam(value = "sandName") String sandName,
                             @RequestParam(value = "position", required = false) String position,
                             @RequestParam(value = "locationId", required = false) Long locationId,
                             @RequestParam(value = "length", required = false) Double length,
                             @RequestParam(value = "width", required = false) Double width,
                             @RequestParam(value = "status") String status) {
        SandLand sandLand = sandLandService.save(id, sandNo, sandName, position, locationId, length, width,status);
        return super.save(objectName, sandLand);
    }


    /**
     * @param id
     * @return 根据id删除
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return sandLandService.delete(id);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        List<SandLand> dataList = sandLandSearchService.findByConditions(param, paramsNum, selectedIds);
        sandLandSearchService.setDataList(dataList);
        sandLandSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return sandLandService.selectAllIds();
    }


    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "sandLand/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return sandLandService.findMaxId();
    }

    /**
     * @param selectedIds 
     * @return 对沿海沙洲列表进行批量审核，仅仅将未审核的数据的status由“0”改为“1”，已审核的就不再审核
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("selectedIds") String selectedIds) {
        List<SandLand> resultList = sandLandService.authorizeInBatch(selectedIds);
        int authedNum = resultList.size();
        int notAuthedNum = (selectedIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "沿海沙洲信息:" + authedNum + "条审核成功，" + notAuthedNum + "条已审核！", "沿海沙洲信息:"+ notAuthedNum + "条已审核！");
    }
}

