package com.yhb.controller.natureReserve;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.natureReserve.NatureReserveExperiment;
import com.yhb.domain.natureReserve.NatureReserveExperiment;
import com.yhb.service.natureReserve.NatureReserveExperimentSearchService;
import com.yhb.service.natureReserve.NatureReserveExperimentService;
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
 * Created by Administrator on 2017/6/22.
 */
@Controller
@RequestMapping("/natureReserveExperiment")
public class NatureReserveExperimentController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 2;
    @Autowired
    NatureReserveExperimentSearchService natureReserveExperimentSearchService;

    @Autowired
    NatureReserveExperimentService natureReserveExperimentService;

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
        return new PageUtils().searchBySortServiceWithSelectedIds(natureReserveExperimentSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
        List<NatureReserveExperiment> dataList = natureReserveExperimentSearchService.findByConditions(param, paramsNum,selectedIds);
        natureReserveExperimentSearchService.setDataList(dataList);
        natureReserveExperimentSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @param natureReserveExperiment
     * @return 保存单个的试验区数据
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(NatureReserveExperiment natureReserveExperiment) {
        natureReserveExperiment = natureReserveExperimentService.save(natureReserveExperiment);
        return super.save(objectName, natureReserveExperiment);
    }


    /**
     * @param id
     * @return 根据id删除试验区数据
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return natureReserveExperimentService.delete(id);
    }

    /**
     * @param id
     * @return 根据id查询试验区数据
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public NatureReserveExperiment findById(@PathVariable("id") Long id) {
        return natureReserveExperimentService.findById(id);
    }

    /**
     * @return 查询所有的自然保护试验区信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<NatureReserveExperiment> findAll() {
        return natureReserveExperimentService.findAll();
    }
    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return natureReserveExperimentService.selectAllIds();
    }




    @RequestMapping(value = "/create")
    public String create() {
        //加载查询菜单
        return "natureReserveExperiment/create";
    }


    /**
     * @return 查询最大的id
     */
    @RequestMapping(value = "/findMaxId", method = RequestMethod.GET)
    @ResponseBody
    public  Long findMaxId(){
        return natureReserveExperimentService.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 对自然保护试验区列表进行批量审核，仅仅将未审核的数据的status由“0”改为“1”，已审核的就不再审核
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject authorizeInBatch(@RequestParam("selectedIds") String selectedIds) {
        List<NatureReserveExperiment> resultList = natureReserveExperimentService.authorizeInBatch(selectedIds);
        int authedNum = resultList.size();
        int notAuthedNum = (selectedIds.split(",").length - authedNum);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), "自然保护试验区信息:" + authedNum + "条审核成功，" + notAuthedNum + "条已审核！", "自然保护试验区信息:"+ notAuthedNum + "条已审核！");
    }
}
