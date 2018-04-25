package com.yhb.controller.media;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.AreaMedia;
import com.yhb.domain.area.VAreaMedia;
import com.yhb.domain.areaProject.ProjectMedia;
import com.yhb.domain.areaProject.VProjectMedia;
import com.yhb.service.media.AreaMediaService;
import com.yhb.service.media.VAreaMediaSearchService;
import com.yhb.service.media.VAreaMediaService;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 围垦区块多媒体库控制器
 */
@Controller
@RequestMapping("/areaMedia")
public class AreaMediaController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;

    @Autowired
    AreaMediaService areaMediaService;

    @Autowired
    VAreaMediaService vAreaMediaService;

    @Autowired
    VAreaMediaSearchService vAreaMediaSearchService;

    String objectName = "区块多媒体资料信息";

    /**
     * @param session
     * @param searchPhrase
     * @return 复合条件查询
     */
    @RequestMapping(value = "/complexSearchByConditions", method = RequestMethod.GET)
    @ResponseBody
    public List<VAreaMedia> complexSearchByConditions(HttpSession session, @RequestParam("searchPhrase") String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        return vAreaMediaSearchService.findByConditions(searchPhrase, paramsNum);
    }

    /**
     * @param areaMedia 保存区块多媒体信息
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(AreaMedia areaMedia) {
        areaMedia = areaMediaService.save(areaMedia);
        return super.save(objectName, areaMedia);
    }

    /**
     * @param areaMediaList 区块多媒体信息列表
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    @ResponseBody
    public List<AreaMedia> save(List<AreaMedia> areaMediaList) {
        return areaMediaService.save(areaMediaList);
    }

    /**
     * @return 根据数据权限查询所有的记录
     */
    @RequestMapping("/findByAuthKey")
    @ResponseBody
    public List<AreaMedia> findByAuthKey(HttpSession session) {
        return areaMediaService.findByAuthKey(session);
    }

    /**
     * @param fileName 多媒体文件名
     * @return 根据多媒体文件名查询
     */
    @RequestMapping("/findByFileName/{fileName}")
    @ResponseBody
    public AreaMedia findByFileName(@PathVariable("fileName") String fileName) {
        return areaMediaService.findByFileName(fileName);
    }

    /**
     * @param id 对象id
     * @return 根据id查询
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public AreaMedia findById(@PathVariable("id") Long id) {
        return areaMediaService.findById(id);
    }


    /**
     * @param idsList ids列表
     * @return 根据ids列表批量删除文件
     */
    @RequestMapping(value = "/deleteInBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<ReturnObject> deleteInBatch(@RequestParam("idsList") List<Long> idsList) {
        return areaMediaService.deleteInBatch(idsList);
    }

    /**
     * @param id
     * @return 根据id删除
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return areaMediaService.delete(id);
    }



    /**
     * 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     * <p>
     * huangbin  overide
     *
     * @param file
     * @param treeNodeId
     * @param request
     * @param session
     * @param userName
     * @return 返回项目多媒体信息
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFileAndSavePath", method = RequestMethod.POST)
    @ResponseBody
    public AreaMedia uploadFileAndSavePath(@RequestParam("file") MultipartFile file, @RequestParam("treeNodeId") Long treeNodeId, @RequestParam("areaId") Long areaId,
                                           HttpServletRequest request, HttpSession session, @RequestParam("userName") String userName) throws Exception {
        return areaMediaService.uploadFileAndSavePath(file, treeNodeId, areaId, request, session, userName);
    }


    /**
     * @param session
     * @param areaName
     * @param authStatus
     * @return 根据 区块名称、数据权限、审核状态 查询该区块所有的多媒体信息列表
     */
    @RequestMapping(value = "/findByAreaNameAndAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VAreaMedia> findByAreaNameAndAuthKeyAndStatus(HttpSession session, @RequestParam("areaName") String areaName, @RequestParam("authStatus") String authStatus) {
        return vAreaMediaService.findByAreaNameAndAuthKeyAndStatus(session, areaName, authStatus);
    }

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    @RequestMapping("/findByMediaTypeId/{mediaTypeId}")
    @ResponseBody
    public List<AreaMedia> findByMediaTypeId(@PathVariable("mediaTypeId") Long mediaTypeId) {
        return areaMediaService.findByMediaTypesId(mediaTypeId);
    }

    /**
     * @param areaId
     * @param mediaTypeId
     * @return 根据区块id、多媒体类型id以及数据权限 查询该属于该区块的该多媒体类型的信息列表
     */
    @RequestMapping(value = "/findByAreaIdAndMediaTypeId", method = RequestMethod.POST)
    @ResponseBody
    public List<AreaMedia> findByAreaIdAndMediaTypeIdAndAuthKey(@RequestParam(value = "areaId") Long areaId,
                                                                @RequestParam(value = "mediaTypeId") Long mediaTypeId, HttpSession session) {
        return areaMediaService.findByAreaIdAndMediaTypesIdAndAuthKey(areaId, mediaTypeId, session);
    }


    /**
     * @param areaId
     * @return 根据区块id、多媒体类型id查询图片
     */
    @RequestMapping(value = "/findPicsByAreaIdAndMediaTypeId", method = RequestMethod.POST)
    @ResponseBody
    public List<AreaMedia> findPicsByAreaId(@RequestParam(value = "areaId") Long areaId, @RequestParam(value = "mediaTypeId", defaultValue = "2l") Long mediaTypeId) {
        return areaMediaService.findPicsByAreaIdAndMediaTypeId(areaId, mediaTypeId);
    }


    /**
     * @param id
     * @return 根据id进行多媒体文件授权
     */
    @RequestMapping(value = "/authorizeById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject authorizeById(@PathVariable(value = "id") Long id) {
        return areaMediaService.authorizeById(id);
    }

    /**
     * @param idsList
     * @return 根据ids列表对多媒体文件进行批量授权
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<ReturnObject> authorizeInBatch(@RequestParam("idsList") List<Long> idsList) {
        return areaMediaService.authorizeInBatch(idsList);
    }

    /**
     * @param session
     * @param mediaTreeCode
     * @param authStatus
     * @return 根据 mediaTreeCode、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    @RequestMapping(value = "/findByTreeNodeCodeAndAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VAreaMedia> findByTreeNodeCodeAndAuthKeyAndStatus(HttpSession session, @RequestParam("treeNodeCode") String mediaTreeCode, @RequestParam("authStatus") String authStatus) {
        return vAreaMediaService.findByTreeNodeCodeAndAuthKeyAndStatus(session, mediaTreeCode, authStatus);
    }

}
