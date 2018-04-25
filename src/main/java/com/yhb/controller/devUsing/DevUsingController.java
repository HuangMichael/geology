package com.yhb.controller.devUsing;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaDevUsing.AreaDevUsing;
import com.yhb.service.devUsing.AreaDevUsingSearchService;
import com.yhb.service.devUsing.AreaDevUsingService;
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
import java.util.List;
import java.util.Map;


/**
 * Created by llm on 2017/5/16 0016.
 * 开发利用控制器
 */
@Controller
@RequestMapping("/devUsing")
public class DevUsingController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 1;
    @Autowired
    AreaDevUsingService areaDevUsingService;

    @Autowired
    AreaDevUsingSearchService  areaDevUsingSearchService;

    String objectName = "开发利用信息";


    /**
     * 分页查询
     *
     * @param current      当前页，值是从1开始
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
        return new PageUtils().searchBySortServiceWithSelectedIds(areaDevUsingSearchService, searchPhrase, paramsNum, current, rowCount, pageable);
    }

    /**
     * @param id
     * @param title
     * @param subTitle
     * @param devUsingDesc
     * @param parentId
     * @param status
     * @return 新增或者编辑 仅修改保存开发利用的基本信息，在上传多媒体资料时才保存它的多媒体资料信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(@RequestParam(value = "id", required = false) Long id,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "subTitle") String subTitle,
                             @RequestParam(value = "devUsingDesc", required = false) String devUsingDesc,
                             @RequestParam(value = "parentId", required = false) Long parentId,
                             @RequestParam(value = "status") String status) {
        AreaDevUsing areaDevUsing = areaDevUsingService.save(id,title,subTitle,devUsingDesc,parentId,status);
        return super.save(objectName, areaDevUsing);
    }

    /**
     * @param id
     * 根据id删除一条记录。如果它有多媒体文件，同时要删除它的多媒体文件
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaDevUsingService.delete(id);
    }


    /**
     * @return 查询所有的记录
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<AreaDevUsing> findAll() {
        return areaDevUsingService.findAll();
    }


    /**
     * @param id 对象id
     * @return 根据id查询记录
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public AreaDevUsing findById(@PathVariable("id") Long id) {
        return areaDevUsingService.findById(id);
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
                            @RequestParam("titles") String titles[], @RequestParam("colNames") String[] colNames, @RequestParam(value = "selectedIds")List<Long> selectedIds,
                            @RequestParam(value = "sort", required = false) String[] sort) {
        List<AreaDevUsing> dataList = areaDevUsingSearchService.findByConditions(param, paramsNum,selectedIds);
        areaDevUsingSearchService.setDataList(dataList);
        areaDevUsingSearchService.exportExcel(request, response, docName, titles, colNames);
    }

    /**
     * @return 选择所有的id列表
     */
    @ResponseBody
    @RequestMapping(value = "/selectAllIds", method = RequestMethod.GET)
    public List<Long> selectAllIds() {
        return areaDevUsingService.selectAllIds();
    }

    /**
     * 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     *
     * @param file
     * @param request
     * @return 返回开发利用多媒体信息上传结果
     * @throws Exception
     */
    @RequestMapping(value = "/uploadAndSave", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject uploadAndSave(@RequestParam("file") MultipartFile file, @RequestParam("areaDevUsingId") Long areaDevUsingId, HttpServletRequest request) throws Exception {
        return areaDevUsingService.uploadAndSave(file, areaDevUsingId, request);
    }


    /**
     * @param level
     * @return 根据level查询所有符合的列表
     */
    @ResponseBody
    @RequestMapping(value = "/findByLevel/{level}", method = RequestMethod.GET)
    public List<AreaDevUsing> findByLevel(@PathVariable("level") Long level) {
        return areaDevUsingService.findByLevel(level);
    }

    /**
     * @param pId 父级id
     * @return 根据pId查询所有符合的列表
     */
    @ResponseBody
    @RequestMapping(value = "/findByParentId/{pId}", method = RequestMethod.GET)
    public List<AreaDevUsing> findByParentId(@PathVariable("pId") Long pId) {
        return areaDevUsingService.findByParentId(pId);
    }

    /**
     * @param pId
     * @return 根据父id查询孩子节点中最大的sortNo，返回最大的排序值
     */
    @ResponseBody
    @RequestMapping(value = "/findMaxSortNoByPId/{pId}", method = RequestMethod.GET)
    public Long findMaxSortNoByPId(@PathVariable("pId")Long pId){
        return areaDevUsingService.findMaxSortNoByPId(pId);
    }

    /**
     * @param level
     * @return 根据级别查询同级别里最大的sortNo，返回最大的排序值maxSortNo
     */
    @ResponseBody
    @RequestMapping(value = "/findMaxSortNoByLevel/{level}", method = RequestMethod.GET)
    public Long findMaxSortNoByLevel(@PathVariable("level")Long level){
        return areaDevUsingService.findMaxSortNoByLevel(level);
    }
}
