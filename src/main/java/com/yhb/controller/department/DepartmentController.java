package com.yhb.controller.department;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.department.Department;
import com.yhb.domain.department.VDepartment;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.department.DepartmentService;
import com.yhb.service.department.VDepartmentSearchService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.service.user.VUserSearchService;
import com.yhb.utils.PageUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import lombok.Data;
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
 * Created by huangbin on 2017/5/17 0004.
 * 部门信息控制器类
 */
@Controller
@RequestMapping("/department")
@Data
public class DepartmentController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    VDepartmentSearchService vDepartmentSearchService;


    @Autowired
    CommonDataService commonDataService;

    String objectName = "部门信息";


    String serviceTableName = "t_department";


    @Autowired
    EtlTableService etlTableService;

    @Autowired
    BaseService baseService;


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
        return new PageUtils().searchBySortServiceWithSelectedIds(vDepartmentSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param id
     * @param depNo
     * @param depName
     * @param depDesc
     * @param manager
     * @param locationId
     * @param parentId
     * @param status
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "depNo") String depNo,
                             @RequestParam(value = "depName") String depName,
                             @RequestParam(value = "depDesc", required = false) String depDesc,
                             @RequestParam(value = "manager", required = false) String manager,
                             @RequestParam(value = "locationId", required = false) Long locationId,
                             @RequestParam(value = "parentId", required = false) Long parentId,
                             @RequestParam(value = "status", required = false) String status) {
        Department department = departmentService.save(id, depNo, depName, depDesc, manager, locationId, parentId, status);
        return super.save(objectName, department);
    }

    /**
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Department> findAll() {
        return departmentService.findAll();
    }


    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Department findById(@PathVariable("id") Long id) {
        return departmentService.findById(id);
    }

    /**
     * @param depName 部门名称
     * @return 根据部门名称查询
     */
    @RequestMapping(value = "/findByDepName", method = RequestMethod.POST)
    @ResponseBody
    public Department findByDepName(@RequestParam(value = "depName") String depName) {
        return departmentService.findByDepName(depName);
    }

    /**
     * @param id 根据ID删除部门信息
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return departmentService.delete(id);
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
        List<VDepartment> dataList = vDepartmentSearchService.findByConditions(param, paramsNum, selectedIds);
        vDepartmentSearchService.setDataList(dataList);
        vDepartmentSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return departmentService.selectAllIds();
    }


    /**
     * @param locName
     * @return 根据位置名称模糊查询
     */
    @ResponseBody
    @RequestMapping(value = "/findByLocName", method = RequestMethod.GET)
    public List<Department> findByLocName(@RequestParam("locName") String locName) {
        return departmentService.findByLocation_LocNameContains(locName);
    }

    /**
     * @param pId
     * @return 根据父id查询它的孩子部门列表
     */
    @RequestMapping("/findByParentId/{pId}")
    @ResponseBody
    public List<Department> findByParentId(@PathVariable("pId") Long pId) {
        return departmentService.findByParentId(pId);
    }

    @RequestMapping(value = "/oneKeyImport", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject oneKeyImport() {
        departmentService.oneKeyImport(this.getServiceTableName());
        log.info(this.getServiceTableName());
        return this.getCommonDataService().getReturnType(true, "数据一键导入成功！", "数据一键导入失败");

    }

    @RequestMapping(value = "/oneKeyExport", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject oneKeyExport(HttpServletRequest request, HttpServletResponse response) {
        //根据业务表名称  查询表的id
        Boolean result = departmentService.oneKeyExport(this.getServiceTableName(), request, response);
        log.info(this.getServiceTableName());
        return this.getCommonDataService().getReturnType(result, "数据一键导出成功！", "数据一键导出失败");
    }

}
