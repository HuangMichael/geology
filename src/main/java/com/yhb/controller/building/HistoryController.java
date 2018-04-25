package com.yhb.controller.building;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.history.History;
import com.yhb.service.building.HistorySearchService;
import com.yhb.service.building.HistoryService;
import com.yhb.utils.PageUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by llm on 2017/5/16 0016.
 * 历史沿革控制器
 */
@Controller
@RequestMapping("/history")
public class HistoryController extends BaseController {

    @Autowired
    HistoryService historyService;

    @Autowired
    HistorySearchService historySearchService;

    String objectName = "历史沿革信息";





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
        return new PageUtils().searchBySortService(historySearchService, searchPhrase, 1, current, rowCount, pageable);
    }


    /**
     * @param id id
     * @param title 主标题
     * @param subTitle 子标题
     * @param historyDesc 历史沿革描述
     * @param parentId 上级历史沿革的id
     * @param status 状态
     * @return 新增或者编辑：仅修改保存历史沿革的基本信息，在上传多媒体资料时才保存它的多媒体资料信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "subTitle") String subTitle,
                             @RequestParam(value = "historyDesc") String historyDesc,
                             @RequestParam(value = "parentId") Long parentId,
                             @RequestParam(value = "status") String status) {
        History history = historyService.save(id,title,subTitle,historyDesc,parentId,status);
        return super.save(objectName, history);
    }

    /**
     * @param id
     * @return 根据id删除一条记录。如果它有多媒体文件，同时要删除它的多媒体文件
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return historyService.delete(id);
    }


    /**
     * @return 查询所有的记录
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<History> findAll() {
        return historyService.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询记录
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public History findById(@PathVariable("id") Long id) {
        return historyService.findById(id);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("param") String param, @RequestParam("docName") String docName, @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds")List<Long> selectedIds,@RequestParam(value = "sort", required = false) String[] sort) {
        List<History> dataList = historySearchService.findByConditions(param, 1, selectedIds);
        historySearchService.setDataList(dataList);
        historySearchService.exportExcel(request, response, docName, titles, colNames);
    }


    /**
     * @return 选择所有的id
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return historyService.selectAllIds();
    }


    /**
     * 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     *
     * @param file
     * @param historyId
     * @param request
     * @return 返回历史沿革多媒体信息上传结果
     * @throws Exception
     */
    @RequestMapping(value = "/uploadAndSave", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject uploadAndSave(@RequestParam("file") MultipartFile file, @RequestParam("historyId") Long historyId, HttpServletRequest request) throws Exception {
        return historyService.uploadAndSave(file, historyId, request);
    }

    /**
     * @param level
     * @return 根据level查询所有符合的列表
     */
    @ResponseBody
    @RequestMapping(value = "/findByLevel/{level}", method = RequestMethod.GET)
    public List<History> findByLevel(@PathVariable("level") Long level) {
        return historyService.findByLevel(level);
    }

    /**
     * @param pId 父级id
     * @return 根据pId查询所有符合的列表
     */
    @ResponseBody
    @RequestMapping(value = "/findByParentId/{pId}", method = RequestMethod.GET)
    public List<History> findByParentId(@PathVariable("pId") Long pId) {
        return historyService.findByParentId(pId);
    }

    /**
     * @param pId
     * @return 根据父id查询孩子节点中最大的sortNo，返回最大的排序值
     */
    @ResponseBody
    @RequestMapping(value = "/findMaxSortNoByPId/{pId}", method = RequestMethod.GET)
    public Long findMaxSortNoByPId(@PathVariable("pId")Long pId){
        return historyService.findMaxSortNoByPId(pId);
    }

    /**
     * @param level
     * @return 根据级别查询同级别里最大的sortNo，返回最大的排序值maxSortNo
     */
    @ResponseBody
    @RequestMapping(value = "/findMaxSortNoByLevel/{level}", method = RequestMethod.GET)
    public Long findMaxSortNoByLevel(@PathVariable("level")Long level){
        return historyService.findMaxSortNoByLevel(level);
    }
}
