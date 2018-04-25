package com.yhb.controller.etl;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.etl.EtlTable;
import com.yhb.domain.etl.EtlTableTree;
import com.yhb.service.etl.EtlTableSearchService;
import com.yhb.service.etl.EtlTableService;
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
 * Created by huangbin on 2017-08-16 09:53:27
 */
@Controller
@RequestMapping("/etlTable")
public class EtlTableController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;
    @Autowired
    EtlTableService etlTableService;

    @Autowired
    EtlTableSearchService etlTableSearchService;

    String objectName = "元数据表信息";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(etlTableSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param etlTable
     * @return 保存区块
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(EtlTable etlTable) {
        etlTableService.save(etlTable);
        return super.save(objectName, etlTable);
    }

    /**
     * @return 查找所有的区块信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<EtlTable> findAll() {
        return etlTableService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public EtlTable findById(@PathVariable("id") Long id) {
        return etlTableService.findById(id);
    }

    /**
     * @param id
     * @return 根据id删除区块信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return etlTableService.delete(id);
    }


    /**
     * @param request  请求
     * @param response 响应
     * @param param    查询关键字
     * @param docName  文档名称
     * @param titles   标题集合
     * @param colNames 列名称
     * @param selectedIds
     * @param sort
     *   导出Excel数据
     */
    @ResponseBody
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames,
                            @RequestParam(value = "selectedIds") List<Long> selectedIds,@RequestParam(value = "sort", required = false) String[] sort) {
        List<EtlTable> dataList = etlTableSearchService.findByConditions(param, paramsNum,selectedIds);
        etlTableSearchService.setDataList(dataList);
        etlTableSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return etlTableService.selectAllIds();
    }


    /**
     * 查询所有的ETL表。形成树状变为ztree的形式
     * @return
     */
    @RequestMapping("/findEtlTableTree")
    @ResponseBody
    public List<EtlTableTree> findEtlTableTree() {
        return etlTableService.findEtlTableTree();
    }
}
