package com.yhb.controller.geo;

import com.yhb.controller.common.BaseController;
import com.yhb.dao.areaProject.AreaProjectDescRepository;
import com.yhb.domain.areaProject.AreaProjectDesc;
import com.yhb.domain.geoLayer.GeoLayer;
import com.yhb.service.geo.GeoLayerSearchService;
import com.yhb.service.geo.GeoLayerService;
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
 * Created by huangbin on 2017/5/22 0004.
 * 图层控制器
 */
@Controller
@RequestMapping("/layer")
public class LayerController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;

    @Autowired
    GeoLayerService geoLayerService;

    @Autowired
    GeoLayerSearchService geoLayerSearchService;

    String objectName = "图层信息";



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
        return new PageUtils().searchBySortServiceWithSelectedIds(geoLayerSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds,
                            @RequestParam(value = "sort", required = false) String[] sort) {
        List<GeoLayer> dataList = geoLayerSearchService.findByConditions(param, paramsNum, selectedIds);
        geoLayerSearchService.setDataList(dataList);
        geoLayerSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @param geoLayer
     * @return 保存地理图层信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(GeoLayer geoLayer) {
        geoLayer = geoLayerService.save(geoLayer);
        return super.save(objectName, geoLayer);
    }

    /**
     * @param id 根据id删除地理图层信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return geoLayerService.delete(id);
    }


    /**
     * @return 查询所有的地理图层信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<GeoLayer> findAll() {
        return geoLayerService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询地理图层信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public GeoLayer findById(@PathVariable("id") Long id) {
        return geoLayerService.findById(id);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return geoLayerService.selectAllIds();
    }




}
