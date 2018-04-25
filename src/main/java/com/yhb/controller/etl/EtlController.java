package com.yhb.controller.etl;


import com.yhb.controller.common.BaseController;
import com.yhb.domain.etl.EtlTable;
import com.yhb.domain.etl.EtlTableConfig;
import com.yhb.domain.student.Student;
import com.yhb.service.etl.EtlSearchService;
import com.yhb.service.etl.EtlService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.utils.PageUtils;
import com.yhb.utils.StringUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import jxl.write.WriteException;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by huangbin on 2017-08-16 09:53:27
 */
@Controller
@RequestMapping("/etl")
public class EtlController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;

    @Autowired
    EtlService etlService;

    @Autowired
    EtlSearchService etlSearchService;

    @Autowired
    EtlTableService etlTableService;

    String objectName = "元数据表属性信息";




    /**
     * @param tableIds
     * @return 根据tableIds导出多个excel模板
     * @throws WriteException
     * @throws IOException
     */
    @RequestMapping(value = "/createExcels", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject createExcels(@RequestParam("tableIds") String tableIds,HttpServletRequest request, HttpServletResponse response) throws WriteException, IOException {
        List<Boolean> resultList = etlTableService.createExcels(tableIds,request,response);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), resultList.size()+"个文件创建成功！", "文件创建失败！");
    }

    /**
     * @param file
     * @param tableId 表id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadAndSave", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject uploadAndSave(@RequestParam("file") MultipartFile file, @RequestParam("tableId") Long tableId, HttpServletRequest request) throws Exception {
        String filePath = etlTableService.uploadAndSave(file, tableId, request);
        //文件路径
        //业务表名称
        int[] importSize = {};
        EtlTable etlTable = etlTableService.findById(tableId);
        List<EtlTableConfig> etlTableConfigList = etlService.findByEtlTableId(tableId);
        //并将数据导入到基础表中
        //判断基础表是否存在  如果不存在  创建基础表
        boolean exist = etlService.isTableExist(etlTable.getBaseTableName());
        log.info("exist base table-----------" + exist);
        etlService.dropBaseTable("sde", etlTable);
        etlService.createBaseTable("sde", etlTable, etlTableConfigList);
        //根据基础表和业务表配置关系  将外键数据导入到业务表中
        try {
            importSize = etlTableService.importData(etlTable, filePath, etlTable.getDomainName(), etlTableConfigList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将数据导入到基础表当中
        log.info("表已经存在 直接导入数据");
        etlService.importServiceDataSql("sde", etlTable, etlTableConfigList);
        return getCommonDataService().getReturnType(importSize.length != 0, "条数据导入成功", "数据导入失败");
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
        return new PageUtils().searchBySortServiceWithSelectedIds(etlSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param etlTableConfig
     * @return 保存元数据表信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(EtlTableConfig etlTableConfig) {
        etlService.save(etlTableConfig);
        return super.save(objectName, etlTableConfig);
    }

    /**
     * @return 查找所有的元数据表信息信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<EtlTableConfig> findAll() {
        return etlService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询元数据表信息信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public EtlTableConfig findById(@PathVariable("id") Long id) {
        return etlService.findById(id);
    }

    /**
     * @param id
     * @return 根据id删除元数据表信息信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return etlService.delete(id);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName,
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds,
                            @RequestParam(value = "sort", required = false) String[] sort) {
        List<EtlTableConfig> dataList = etlSearchService.findByConditions(param, paramsNum,selectedIds);
        etlSearchService.setDataList(dataList);
        etlSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return etlService.selectAllIds();
    }

    /**
     * @param etlTableId
     * @return 根据etlTableId查询该表的所有属性信息EtlTableConfig
     */
    @ResponseBody
    @RequestMapping(value = "/findByEtlTableId/{etlTableId}", method = RequestMethod.GET)
    public List<EtlTableConfig> findByEtlTableId(@PathVariable("etlTableId") Long etlTableId) {
        return etlService.findByEtlTableId(etlTableId);
    }
}


