package com.yhb.controller.systemManage;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaBuilding.AreaBuilding;
import com.yhb.domain.areaBuilding.VAreaBuilding;
import com.yhb.domain.areaBuilding.VcityBuilding;
import com.yhb.domain.location.LocationBuilding;
import com.yhb.service.building.AreaBuildingService;
import com.yhb.service.building.VAreaBuildingSearchService;
import com.yhb.service.building.VAreaBuildingService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.StringUtils;
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
 * Created by Administrator on 2017/10/13.
 */
@Controller
@RequestMapping("/sysMngAreaBuilding")
public class SysMngAreaBuildingController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 8;
    @Autowired
    AreaBuildingService areaBuildingService;

    @Autowired
    VAreaBuildingService vAreaBuildingService;

    @Autowired
    VAreaBuildingSearchService vAreaBuildingSearchService;

    String objectName = "围垦信息";



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
        return new PageUtils().searchBySortServiceWithSelectedIds(vAreaBuildingSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
        List<VAreaBuilding> dataList = vAreaBuildingSearchService.findByConditions(param, paramsNum, selectedIds);
        vAreaBuildingSearchService.setDataList(dataList);
        vAreaBuildingSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @param id 对象id
     * @return 围垦信息
     */
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public AreaBuilding findById(@PathVariable("id") Long id) {
        return areaBuildingService.findById(id);
    }

    /**
     * @param id 对象id
     * @return 围垦信息视图
     */
    @RequestMapping(value = "/findVById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public VAreaBuilding findVById(@PathVariable("id") Long id) {
        return vAreaBuildingService.findById(id);
    }

    /**
     * @return 围垦信息列表
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<AreaBuilding> findAll() {
        return areaBuildingService.findAll();
    }

    /**
     * @return 围垦信息视图列表
     */
    @RequestMapping(value = "/findVAll", method = RequestMethod.GET)
    @ResponseBody
    public List<VAreaBuilding> findVAll() {
        return vAreaBuildingService.findAll();
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    @RequestMapping(value = "/area/areaTypeSize/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> findAreaTypeSizeByAreaId(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findAreaTypeSizeByAreaId(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据求和
     */
    @RequestMapping(value = "/area/areaTypeSizeTotal/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object findAreaTypeSizeTotalByAreaId(@PathVariable("areaId") Long areaId) {
        return areaBuildingService.findAreaTypeSizeTotalByAreaId(areaId);
    }

    /**
     * @return 查询某区块的已围垦的用地类型统计数据求和，并按照区块编号分组
     */
    @RequestMapping(value = "/area/typeSizeGroupByAreaNo", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> findAreaTypeSizeGroupByAreaNo(HttpSession session, @RequestParam(value = "searchPhrase", required = false) String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        //1、首先获取符合查询条件的所有记录编号列表areaNoList
        //2、然后对areaNoList去除重复元素----去除重复的区块编号，保证所有的编号唯一
        //3、再去查询用地类型统计数据求和后的值
        List<String> areaNoList = vAreaBuildingSearchService.getAreaNoListByConditions(searchPhrase, paramsNum);
        areaNoList = StringUtils.removeDuplicateElements(areaNoList);//去除重复的区块编号，保证所有的编号唯一
        return vAreaBuildingService.findAreaTypeSizeGroupByAreaNo(areaNoList);
    }


    /**
     * @return 根据城市统计查询围垦面积
     */
    @RequestMapping(value = "/sumByCity", method = RequestMethod.GET)
    @ResponseBody
    public List<VcityBuilding> sumByCity() {
        return vAreaBuildingService.sumByCity();
    }


    /**
     * @return 根据城市统计查询围垦面积
     */
    @RequestMapping(value = "/getLocationBuilding/{id}", method = RequestMethod.GET)
    @ResponseBody
    public LocationBuilding getLocationBuilding(@PathVariable("id") Long id) {
        return vAreaBuildingService.getLocationBuilding(id);
    }

    /**
     * @return 查询所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vAreaBuildingService.selectAllIds();
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaBuildingService.delete(id);
    }

    /**
     * @param areaBuildingIds
     * @return 对围垦进度列表进行批量审核，status由“0”改为“1”
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("areaBuildingIds") String areaBuildingIds){
        List<AreaBuilding> resultList = areaBuildingService.authorizeInBatch(areaBuildingIds);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), resultList.size() + "个围垦进度信息审核成功！", "围垦进度信息审核失败！");
    }
}
