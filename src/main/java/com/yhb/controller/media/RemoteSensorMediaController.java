package com.yhb.controller.media;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.ProjectMedia;
import com.yhb.domain.areaProject.VProjectMedia;
import com.yhb.domain.remoteSensorMedia.RemoteSensorMedia;
import com.yhb.domain.remoteSensorMedia.VRemoteSensorMedia;
import com.yhb.service.media.RemoteSensorMediaService;
import com.yhb.service.media.VRemoteSensorMediaSearchService;
import com.yhb.service.media.VRemoteSensorMediaService;
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
 * 遥感多媒体库控制器类
 */
@Controller
@RequestMapping("/remoteSensorMedia")
public class RemoteSensorMediaController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;

    @Autowired
    RemoteSensorMediaService remoteSensorMediaService;

    @Autowired
    VRemoteSensorMediaService vRemoteSensorMediaService;

    @Autowired
    VRemoteSensorMediaSearchService vRemoteSensorMediaSearchService;

    String objectName = "遥感多媒体资料信息";



    /**
     * @param session
     * @param searchPhrase
     * @return 复合条件查询
     */
    @RequestMapping(value = "/complexSearchByConditions", method = RequestMethod.GET)
    @ResponseBody
    public List<VRemoteSensorMedia> complexSearchByConditions(HttpSession session, @RequestParam("searchPhrase") String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        return vRemoteSensorMediaSearchService.findByConditions(searchPhrase, paramsNum);
    }

    /**
     * @param session
     * @param locationCode
     * @param authStatus
     * @return 根据 位置编码、数据权限、审核状态 查询该位置下各级 的多媒体信息列表
     */
    @RequestMapping(value = "/findByLocationCodeAndAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VRemoteSensorMedia> findByLocationCodeAndAuthKeyAndStatus(HttpSession session, @RequestParam("locationCode") String locationCode, @RequestParam("authStatus") String authStatus) {
        return vRemoteSensorMediaService.findByLocationCodeAndAuthKeyAndStatus(session, locationCode, authStatus);
    }

    /**
     * @param remoteSensorMedia 保存遥感多媒体信息
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(RemoteSensorMedia remoteSensorMedia) {
        remoteSensorMedia = remoteSensorMediaService.save(remoteSensorMedia);
        return super.save(objectName, remoteSensorMedia);
    }

    /**
     * @param remoteSensorMediaList 遥感多媒体信息列表
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    @ResponseBody
    public List<RemoteSensorMedia> save(List<RemoteSensorMedia> remoteSensorMediaList) {
        return remoteSensorMediaService.save(remoteSensorMediaList);
    }

    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public RemoteSensorMedia findById(@PathVariable("id") Long id) {
        return remoteSensorMediaService.findById(id);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return remoteSensorMediaService.delete(id);
    }

    /**
     * @param idsList ids列表
     * @return 根据ids列表批量删除文件
     */
    @RequestMapping(value = "/deleteInBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<ReturnObject> deleteInBatch(@RequestParam("idsList") List<Long> idsList) {
        return remoteSensorMediaService.deleteInBatch(idsList);
    }

    /**
     * @param file
     * @param request
     * @return 上传文件
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        return remoteSensorMediaService.upload(file, request);
    }

    /**
     * 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     *
     * @param file
     * @param request
     * @return 返回遥感多媒体
     * @throws Exception
     */
    @RequestMapping(value = "/uploadAndSave", method = RequestMethod.POST)
    @ResponseBody
    public RemoteSensorMedia uploadAndSave(@RequestParam("file") MultipartFile file, @RequestParam("locationId") Long locationId,
                                           HttpServletRequest request, HttpSession session,@RequestParam("userName") String userName) throws Exception {
        return remoteSensorMediaService.uploadAndSave(file, locationId, request, session,userName);
    }

    /**
     * @param locationId
     * @return 根据位置id查询该位置的所有的多媒体信息列表
     */
    @RequestMapping("/findByLocationId/{locationId}")
    @ResponseBody
    public List<RemoteSensorMedia> findByLocationIdAndAuthKey(@PathVariable("locationId") Long locationId, HttpSession session) {
        return remoteSensorMediaService.findByLocationIdAndAuthKey(locationId, session);
    }

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    @RequestMapping("/findByMediaTypeId/{mediaTypeId}")
    @ResponseBody
    public List<RemoteSensorMedia> findByMediaTypeId(@PathVariable("mediaTypeId") Long mediaTypeId) {
        return remoteSensorMediaService.findByMediaTypesId(mediaTypeId);
    }

    /**
     * @param locationId
     * @param mediaTypeId
     * @param session
     * @return 根据遥感id以及多媒体类型id查询该属于该遥感的该多媒体类型的信息列表
     */
    @RequestMapping(value = "/findByLocationIdAndMediaTypeId", method = RequestMethod.POST)
    @ResponseBody
    public List<RemoteSensorMedia> findByLocationIdAndMediaTypeIdAndAuthKey(@RequestParam(value = "locationId") Long locationId,
                                                                            @RequestParam(value = "mediaTypeId") Long mediaTypeId, HttpSession session) {
        return remoteSensorMediaService.findByLocationIdAndMediaTypesIdAndAuthKey(locationId, mediaTypeId, session);
    }

    /**
     * @param id
     * @return 根据id进行多媒体文件授权
     */
    @RequestMapping(value = "/authorizeById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject authorizeById(@PathVariable(value = "id") Long id) {
        return remoteSensorMediaService.authorizeById(id);
    }

    /**
     * @param idsList
     * @return 根据ids列表对多媒体文件进行批量授权
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<ReturnObject> authorizeInBatch(@RequestParam("idsList") List<Long> idsList) {
        return remoteSensorMediaService.authorizeInBatch(idsList);
    }



    /**
     * @param session
     * @param mediaTreeCode
     * @param authStatus
     * @return 根据 mediaTreeCode、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    @RequestMapping(value = "/findByTreeNodeCodeAndAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VRemoteSensorMedia> findByTreeNodeCodeAndAuthKeyAndStatus(HttpSession session, @RequestParam("treeNodeCode") String mediaTreeCode, @RequestParam("authStatus") String authStatus) {
        return vRemoteSensorMediaService.findByTreeNodeCodeAndAuthKeyAndStatus(session, mediaTreeCode, authStatus);
    }



    /**
     * 上传文件到文件服务器，若上传成功则将文件路径等基本信息保存在数据库
     *
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
    public RemoteSensorMedia  uploadFileAndSavePath(@RequestParam("file") MultipartFile file, @RequestParam("treeNodeId") Long treeNodeId,
                                              HttpServletRequest request, HttpSession session, @RequestParam("userName") String userName) throws Exception {
        return remoteSensorMediaService.uploadFileAndSavePath(file, treeNodeId, request, session, userName);
    }
}
