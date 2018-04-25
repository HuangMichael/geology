package com.yhb.controller.beach;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.beach.Beach;
import com.yhb.service.beach.BeachSearchService;
import com.yhb.service.beach.BeachService;
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
@RequestMapping("/beach")
public class BeachController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 2;

    @Autowired
    BeachSearchService beachSearchService;

    @Autowired
    BeachService beachService;

    String objectName = "滩涂信息";


    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "/beach/create";
    }


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
        return new PageUtils().searchBySortServiceWithSelectedIds(beachSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param id
     * @param beachName
     * @param locationId
     * @param seaLevelTypeId
     * @param measureDate
     * @param inningStatus
     * @return 保存滩涂信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "beachNo") String beachNo,
                             @RequestParam(value = "beachName") String beachName,
                             @RequestParam(value = "locationId", required = false) Long locationId,
                             @RequestParam(value = "seaLevelTypeId", required = false) Long seaLevelTypeId,
                             @RequestParam(value = "measureDate", required = false) String measureDate,
                             @RequestParam(value = "inningStatus", required = false) String inningStatus,
                             @RequestParam(value = "status") String status) {
        Beach beach = beachService.save(id, beachNo, beachName, locationId, seaLevelTypeId, measureDate, inningStatus, status);
        return super.save(objectName, beach);
    }

    /**
     * @return 查询所有的滩涂信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Beach> findAll() {
        return beachService.findAll();
    }


    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Beach findById(@PathVariable("id") Long id) {
        return beachService.findById(id);
    }


    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return beachService.delete(id);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String
            param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds,
                            @RequestParam(value = "sort", required = false) String[] sort) {
        List<Beach> dataList = beachSearchService.findByConditions(param, paramsNum, selectedIds);
        beachService.setDataList(dataList);
        beachService.exportExcel(request, response, docName, titles, colNames);
    }

    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return beachService.selectAllIds();
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxId() {
        return beachService.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 对滩涂列表进行批量审核，仅仅将未审核的数据的status由“0”改为“1”，已审核的就不再审核
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("selectedIds") String selectedIds) {
        List<Beach> resultList = beachService.authorizeInBatch(selectedIds);
        int authedNum = resultList.size();
        int notAuthedNum = (selectedIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "滩涂信息:" + authedNum + "条审核成功，" + notAuthedNum + "条已审核！", "滩涂信息:"+ notAuthedNum + "条已审核！");
    }
}
