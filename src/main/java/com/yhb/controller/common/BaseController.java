package com.yhb.controller.common;


import com.yhb.service.common.CommonDataService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.service.menu.MenuService;
import com.yhb.utils.StringUtils;
import com.yhb.utils.search.AnnotationUtil;
import com.yhb.vo.ReturnObject;
import jxl.write.WriteException;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 基础控制器
 */
@Controller
@Data
public abstract class BaseController {

    protected Log log = LogFactory.getLog(this.getClass());
    @Autowired
    CommonDataService commonDataService;

    @Autowired
    AnnotationUtil annotationUtil;

    @Autowired
    EtlTableService etlTableService;

    @Autowired
    MenuService menuService;

    protected int paramsNum;

    protected String objectName = "";

    protected String serviceTableName = "";


    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        return annotationUtil.getAppPage(this.getClass(), "index");
    }


    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request) {
        return annotationUtil.getAppPage(this.getClass(), "list");
    }


    @RequestMapping(value = "/index3d")
    public String index3d(HttpServletRequest request) {
        return annotationUtil.getAppPage(this.getClass(), "index3d");
    }



    /**
     * @param object 保存对象类型
     * @return 返回操作值对象
     */
    protected ReturnObject save(String objectName, Object object) {
        return commonDataService.getReturnType(object != null, objectName + "保存成功", objectName + "保存失败");
    }


    /**
     * @param id 对象id
     * @return 根据id查询 对象
     */
    @ResponseBody
    protected Object findById(Long id) {

        return null;
    }


    /**
     * @param objectName 处理的对象中文名称
     * @param object     处理的对象
     * @return 返回操作值删除对象结果
     */
    protected ReturnObject delete(String objectName, Object object) {
        return commonDataService.getReturnType(object == null, objectName + "删除成功", objectName + "删除失败");
    }


    /**
     * @param requestMap
     * @return 获取排序的map
     * 第0位为排序顺序  asc desc
     * 第1位为排序字段
     */
    public Sort getSort(Map<String, String[]> requestMap) {
        String sortName = "id";
        Sort.Direction direction = Sort.Direction.ASC; //默认升序排列
        String directionStr = "asc";
        for (String key : requestMap.keySet()) {
            //如果key中以sort开头 获取它的值
            if (key.startsWith("sort")) {
                sortName = (key != null) ? key.split("\\[|\\]")[1] : sortName;
                directionStr = (requestMap.get(key)[0] != null) ? requestMap.get(key)[0] : directionStr;
                break;
            }
        }
        if (directionStr.equals("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = new Sort(direction, sortName);
        return sort;
    }


    /**
     * @param tableIds
     * @return 根据tableIds导出多个excel模板
     * @throws WriteException
     * @throws IOException
     */
    @RequestMapping(value = "/createExcels", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject createExcels(@RequestParam("tableIds") String tableIds, HttpServletRequest request, HttpServletResponse response) throws WriteException, IOException {
        List<Boolean> resultList = etlTableService.createExcels(tableIds, request, response);
        return getCommonDataService().getReturnType(!resultList.isEmpty(), resultList.size() + "个文件创建成功！", "文件创建失败！");
    }


    /**
     * @param tableId
     * @return 根据tableId导出一个excel模板
     * @throws WriteException
     * @throws IOException
     */
    @RequestMapping(value = "/createOneExcel", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject createOneExcel(@RequestParam("tableId") Long tableId, HttpServletRequest request, HttpServletResponse response) throws WriteException, IOException {
        Boolean result = etlTableService.createOneExcel(tableId, request, response);
        return getCommonDataService().getReturnType(result, "文件创建成功！", "文件创建失败！");
    }
}

