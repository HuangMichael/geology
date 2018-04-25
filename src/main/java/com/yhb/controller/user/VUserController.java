package com.yhb.controller.user;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.user.VUser;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.service.user.VUserSearchService;
import com.yhb.service.user.VUserService;
import com.yhb.utils.PageUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 用户控制器类
 */
@Controller
@RequestMapping("/vUser")
@Data
public class VUserController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;

    @Autowired
    VUserService vUserService;

    String serviceTableName = "t_user";

    @Autowired
    VUserSearchService vUserSearchService;

    @Autowired
    EtlTableService etlTableService;

    String objectName = "用户信息";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(vUserSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param,
                            @RequestParam("docName") String docName, @RequestParam("titles") String titles[],
                            @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds,@RequestParam(value = "sort", required = false) String[] sort) {
        List<VUser> dataList = vUserSearchService.findByConditions(param, paramsNum,selectedIds);
        vUserService.setDataList(dataList);
        vUserService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的用户ID
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return vUserService.selectAllIds();
    }



    @RequestMapping(value = "/oneKeyImport", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject oneKeyImport() {
        this.getVUserService().oneKeyImport(this.getServiceTableName());
        log.info(this.getServiceTableName());
        return this.getCommonDataService().getReturnType(true, "数据一键导入成功！", "数据一键导入失败");

    }

    @RequestMapping(value = "/oneKeyExport", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject oneKeyExport(HttpServletRequest request,HttpServletResponse response) {
        //根据业务表名称  查询表的id
        Boolean result =  this.getVUserService().oneKeyExport(this.getServiceTableName(),request,response);
        log.info(this.getServiceTableName());
        return this.getCommonDataService().getReturnType(result, "数据一键导出成功！", "数据一键导出失败");
    }




}
