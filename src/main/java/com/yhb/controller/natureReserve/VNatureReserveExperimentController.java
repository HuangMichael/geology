package com.yhb.controller.natureReserve;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.natureReserve.VNatureReserveExperiment;
import com.yhb.service.natureReserve.VNatureReserveExperimentSearchService;
import com.yhb.service.natureReserve.VNatureReserveExperimentService;
import com.yhb.utils.PageUtils;
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
 * Created by Administrator on 2018/3/14.
 */
@Controller
@RequestMapping("/vNatureReserveExperiment")
public class VNatureReserveExperimentController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 2;

    @Autowired
    VNatureReserveExperimentSearchService vNatureReserveExperimentSearchService;

    @Autowired
    VNatureReserveExperimentService vNatureReserveExperimentService;

    String objectName = "自然保护试验区信息";



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
        return new PageUtils().searchBySortServiceWithSelectedIds(vNatureReserveExperimentSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<VNatureReserveExperiment> findAll() {
        return vNatureReserveExperimentService.findAll();
    }


    @RequestMapping("/findById/{id}")
    @ResponseBody
    public VNatureReserveExperiment findById(@PathVariable("id") Long id) {
        return vNatureReserveExperimentService.findById(id);
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
        List<VNatureReserveExperiment> dataList = vNatureReserveExperimentSearchService.findByConditions(param, paramsNum,selectedIds);
        vNatureReserveExperimentSearchService.setDataList(dataList);
        vNatureReserveExperimentSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vNatureReserveExperimentService.selectAllIds();
    }
}

