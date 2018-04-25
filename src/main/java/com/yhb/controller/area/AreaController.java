package com.yhb.controller.area;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.Area;
import com.yhb.domain.area.AreaTree;
import com.yhb.domain.area.VArea;
import com.yhb.service.area.AreaSearchService;
import com.yhb.service.area.AreaService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import com.yhb.vo.area.Area4Sel;
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
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;

    @Autowired
    AreaService areaService;
    @Autowired
    AreaSearchService areaSearchService;

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
        return new PageUtils().searchBySortServiceWithSelectedIds(areaSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param id
     * @param areaNo
     * @param areaName
     * @param areaDesc
     * @param cityId
     * @param districtId
     * @param avgHeight
     * @param functionTypeId
     * @param areaTypeId
     * @param status
     * @return 保存区块
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(HttpSession session,
                             @RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "areaNo") String areaNo,
                             @RequestParam(value = "areaName") String areaName,
                             @RequestParam(value = "areaDesc", required = false) String areaDesc,
                             @RequestParam(value = "cityId", required = false) Long cityId,
                             @RequestParam(value = "districtId", required = false) Long districtId,
                             @RequestParam(value = "avgHeight", required = false) Double avgHeight,
                             @RequestParam(value = "functionTypeId", required = false) Long functionTypeId,
                             @RequestParam(value = "areaTypeId", required = false) Long areaTypeId,
                             @RequestParam(value = "status") String status,
                             @RequestParam(value = "inningStatus", required = false) String inningStatus,
                             @RequestParam(value = "buildSize", required = false) Double buildSize,
                             @RequestParam(value = "inningSize", required = false) Double inningSize,
                             @RequestParam(value = "locDesc", required = false) String locDesc,
                             @RequestParam(value = "mainPurpose", required = false) String mainPurpose) {
        Area area = areaService.save(session, id, areaNo, areaName, areaDesc, cityId, districtId, avgHeight, functionTypeId, areaTypeId, status, inningStatus, buildSize,
                inningSize, locDesc, mainPurpose);
        return super.save(objectName, area);
    }

    /**
     * @return 查询所有区块，按照区块编号排序
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Area> findAll() {
        return areaService.findAll();
    }


    /**
     * @return 查询所有区块，按照区块编号排序
     */
    @RequestMapping("/findAll4Sel")
    @ResponseBody
    public List<Area4Sel> findAll4Sel() {
        return areaService.findAll4Sel();
    }

    /**
     * @return 根据位置id查询该位置的区块信息，按照区块编号排序
     */
    @RequestMapping("/findAll4SelByLocId/{locId}")
    @ResponseBody
    public List<Area4Sel> findAll4SelByLocId(@PathVariable("locId") Long locId) {
        return areaService.findAll4SelByLocId(locId);
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
     * @return 根据id删除区块信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaService.delete(id);
    }


    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     *                 <p>
     *                 导出Excel数据
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpSession session, HttpServletResponse response,
                            @RequestParam("param") String param, @RequestParam("docName") String docName, @RequestParam("titles") String titles[],
                            @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds, @RequestParam(value = "sort", required = false) String[] sort) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        param = DataFilterUtils.getSearchString(session, param, paramsNum);

        List<Area> dataList = areaSearchService.findByConditions(param, paramsNum, selectedIds);
        areaService.setDataList(dataList);
        areaService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaService.selectAllIds();
    }

    /**
     * @return 查询区块树
     */
    @ResponseBody
    @RequestMapping(value = "/findAreaTree", method = RequestMethod.GET)
    public List<AreaTree> findAreaTreeByAuthKey(HttpSession session) {
        return areaService.findAreaTreeByAuthKey(session);
    }

    /**
     * @return 查询区块树, 而且根据授权码和审核状态查询
     */
    @ResponseBody
    @RequestMapping(value = "/findAreaTreeByAuthKeyAndStatus", method = RequestMethod.POST)
    public List<AreaTree> findAreaTreeByAuthKeyAndStatus(HttpSession session, @RequestParam("authStatus") String authStatus) {
        return areaService.findAreaTreeByAuthKeyAndStatus(session, authStatus);
    }


    /**
     * @param areaNo
     * @return 根据区块编号查询区块信息
     */
    @RequestMapping(value = "/findByAreaNo", method = RequestMethod.POST)
    @ResponseBody
    public Area findByAreaNo(@RequestParam(value = "areaNo") String areaNo) {
        return areaService.findByAreaNo(areaNo);
    }

    /**
     * @param id
     * @param areaNo
     * @return 编辑区块信息时，根据前端传过来的用户输入的新的区块编号和id查询区块编号是否与其他的不等于id的记录重复。如果重复，返回一个Area；不重复返回null
     */
    @RequestMapping(value = "/findAreaDuplicateByNoAndId", method = RequestMethod.POST)
    @ResponseBody
    public Area findAreaDuplicateByNoAndId(@RequestParam("id") Long id, @RequestParam("areaNo") String areaNo) {
        return areaService.findAreaDuplicateByNoAndId(id, areaNo);
    }

    /**
     * @param areaName
     * @return 根据区块名称查询区块信息
     */
    @RequestMapping(value = "/findByAreaName", method = RequestMethod.POST)
    @ResponseBody
    public Area findByAreaName(@RequestParam(value = "areaName") String areaName) {
        return areaService.findByAreaName(areaName);
    }

    /**
     * @param id
     * @param areaName
     * @return 编辑区块信息时，根据前端传过来的用户输入的新的区块名称和id查询是否与其他的重复。如果重复，返回一个Area；不重复返回null
     */
    @RequestMapping(value = "/findAreaDuplicateByNameAndId", method = RequestMethod.POST)
    @ResponseBody
    public Area findAreaDuplicateByNameAndId(@RequestParam("id") Long id, @RequestParam("areaName") String areaName) {
        return areaService.findAreaDuplicateByNameAndId(id, areaName);
    }

    /**
     * @param areaIds
     * @return 对区块列表进行批量审核，将status由“0”改为“1”
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("areaIds") String areaIds) {
        List<Area> resultList = areaService.authorizeInBatch(areaIds);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), resultList.size() + "个区块信息审核成功！", "区块信息审核失败！");
    }


    /**
     * @return 地图显示区块的相册
     */
    @RequestMapping(value = "/showGisGallery", method = RequestMethod.GET)
    public String showGisGallery() {
        return "situation/areaGallery";
    }
}
