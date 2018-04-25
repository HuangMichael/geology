package com.yhb.controller.log;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.log.UserLog;
import com.yhb.domain.person.Person;
import com.yhb.service.department.DepartmentService;
import com.yhb.service.person.PersonSearchService;
import com.yhb.service.person.PersonService;
import com.yhb.service.userLog.UserLogSearchService;
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
 * Created by huangbin on 2017-08-17 13:45:56
 * 用户日志控制器类
 */
@Controller
@RequestMapping("/userLog")
@Data
public class UserLogController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;

    @Autowired
    UserLogSearchService userLogSearchService;
    String objectName = "用户日志信息";


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
        return new PageUtils().searchBySortServiceWithSelectedIds(userLogSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
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
        List<UserLog> dataList = userLogSearchService.findByConditions(param, paramsNum,selectedIds);
        userLogSearchService.setDataList(dataList);
        userLogSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return userLogSearchService.selectAllIds();
    }
}
