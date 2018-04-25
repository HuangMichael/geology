package com.yhb.controller.sysInfo;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.sysInfo.SysParams;
import com.yhb.service.sysInfo.SysParamsSearchService;
import com.yhb.service.sysInfo.SysParamsService;
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
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


/**
 * Created by huangbin on 2017/5/4 0004.
 */
@Controller
@RequestMapping("/sysParams")
public class SysParamsController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;
    @Autowired
    SysParamsService sysParamsService;

    @Autowired
    SysParamsSearchService sysParamsSearchService;


    String objectName = "系统参数信息";



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
        return new PageUtils().searchBySortServiceWithSelectedIds(sysParamsSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
        List<SysParams> dataList = sysParamsSearchService.findByConditions(param, paramsNum,selectedIds);
        sysParamsSearchService.setDataList(dataList);
        sysParamsSearchService.exportExcel(request, response, docName, titles, colNames);
    }
    
    /**
     * @param paramName 参数名称
     * @return 根据参数名称查询对象
     */
    @RequestMapping(value = "/findByParamName", method = RequestMethod.POST)
    @ResponseBody
    public SysParams findByParamName(@RequestParam("paramName") String paramName) {
        return sysParamsService.findByParamName(paramName);
    }

    /**
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return sysParamsService.selectAllIds();
    }

    /**
     * @return 查询所有系统参数信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<SysParams> findAll() {
        return sysParamsService.findAll();
    }


    /**
     * @param id 对象id
     * @return 根据ID查询系统参数信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public SysParams findById(@PathVariable("id") Long id) {
        return sysParamsService.findById(id);
    }

    /**
     * @param sysParams 
     * @return 保存系统参数信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(SysParams sysParams) {
        sysParams = sysParamsService.save(sysParams);
        return super.save(objectName, sysParams);
    }

    /**
     * @param id 根据ID删除系统参数信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return sysParamsService.delete(id);
    }
}
