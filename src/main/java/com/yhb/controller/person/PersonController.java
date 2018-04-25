package com.yhb.controller.person;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.person.Gender;
import com.yhb.domain.person.Person;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.person.PersonSearchService;
import com.yhb.service.person.PersonService;
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
 * 人员控制器类
 */
@Controller
@RequestMapping("/person")
@Data
public class PersonController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 2;
    @Autowired
    PersonService service;
    @Autowired
    PersonSearchService personSearchService;

    @Autowired
    CommonDataService commonDataService;

    String objectName = "人员信息";

    String serviceTableName = "t_person";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(personSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }


    /**
     * @param personName
     * @param gender
     * @param birthDate
     * @param status
     * @return 保存人员信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "personName") String personName,
                             @RequestParam(value = "gender") Long gender,
                             @RequestParam(value = "birthDate") String birthDate,
                             @RequestParam(value = "departmentId") Long depId,
                             @RequestParam(value = "status") String status) {

        Person person = service.save(id, personName, gender, birthDate, depId, status);
        return super.save(objectName, person);
    }

    /**
     * @return 查询所有人员信息
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<Person> findAll() {
        return service.findAll();
    }


    /**
     * @param id 对象id
     * @return 根据ID查询人员信息
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public Person findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param personName 人员名称
     * @return 根据人员名称查询人员信息
     */
    @RequestMapping("/findByPersonName/{personName}")
    @ResponseBody
    public Person findByPersonName(@PathVariable("personName") String personName) {
        return service.findByPersonName(personName);
    }

    /**
     * @param id 根据ID删除人员信息
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return service.delete(id);
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
                            @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds") List<Long> selectedIds,
                            @RequestParam(value = "sort", required = false) String[] sort) {
        List<Person> dataList = personSearchService.findByConditions(param, paramsNum, selectedIds);
        service.setDataList(dataList);
        service.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 查询所有的人员ID
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return service.selectAllIds();
    }

    /**
     * @return 查询所有人员信息
     */
    @RequestMapping("/findGenders")
    @ResponseBody
    public List<Gender> findGenders() {
        return service.findGenders();
    }



    @RequestMapping(value = "/oneKeyImport", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject oneKeyImport() {
        this.getService().oneKeyImport(this.getServiceTableName());
        log.info(this.getServiceTableName());
        return commonDataService.getReturnType(true, "数据一键导入成功！", "数据一键导入失败");

    }

    @RequestMapping(value = "/oneKeyExport", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject oneKeyExport(HttpServletRequest request,HttpServletResponse response) {
        //根据业务表名称  查询表的id
        this.getService().oneKeyExport(this.getServiceTableName(),request,response);
        log.info(this.getServiceTableName());
        return commonDataService.getReturnType(true, "数据一键导出成功！", "数据一键导出失败");
    }
}
