package com.yhb.controller.media;

import com.yhb.archives.Archives;
import com.yhb.controller.common.BaseController;
import com.yhb.domain.area.AreaMedia;
import com.yhb.domain.areaProject.ProjectMedia;
import com.yhb.domain.areaProject.VProjectMedia;
import com.yhb.service.media.ProjectMediaService;
import com.yhb.service.media.VProjectMediaSearchService;
import com.yhb.service.media.VProjectMediaService;
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
 * 项目多媒体库控制器
 */
@Controller
@RequestMapping("/projectMedia")
public class ProjectMediaController extends BaseController {
    //复合查询条件参数个数
    private final int paramsNum = 6;

    @Autowired
    ProjectMediaService projectMediaService;

    @Autowired
    VProjectMediaService vProjectMediaService;

    @Autowired
    VProjectMediaSearchService vProjectMediaSearchService;

    String objectName = "项目多媒体资料信息";


    /**
     * @param session
     * @param searchPhrase
     * @return 复合条件查询
     */
    @RequestMapping(value = "/complexSearchByConditions", method = RequestMethod.GET)
    @ResponseBody
    public List<VProjectMedia> complexSearchByConditions(HttpSession session, @RequestParam("searchPhrase") String searchPhrase) {
        //对前端传来的查询关键字字符串重新组装，加上授权码AuthKey
        searchPhrase = DataFilterUtils.getSearchString(session, searchPhrase, paramsNum);
        return vProjectMediaSearchService.findByConditions(searchPhrase, paramsNum);
    }

    /**
     * @param projectMedia 保存项目多媒体信息
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ReturnObject save(ProjectMedia projectMedia) {
        projectMedia = projectMediaService.save(projectMedia);
        return super.save(objectName, projectMedia);
    }

    /**
     * @param projectMediaList 项目多媒体信息列表
     * @return
     */
    @RequestMapping(value = "/saveList", method = RequestMethod.POST)
    @ResponseBody
    public List<ProjectMedia> save(List<ProjectMedia> projectMediaList) {
        return projectMediaService.save(projectMediaList);
    }

    /**
     * @param id 对象id
     * @return
     */
    @RequestMapping("/findById/{id}")
    @ResponseBody
    public ProjectMedia findById(@PathVariable("id") Long id) {
        return projectMediaService.findById(id);
    }

    /**
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ReturnObject delete(@PathVariable("id") Long id) {
        return projectMediaService.delete(id);
    }

    /**
     * @param idsList ids列表
     * @return 根据ids列表批量删除文件
     */
    @RequestMapping(value = "/deleteInBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<ReturnObject> deleteInBatch(@RequestParam("idsList") List<Long> idsList) {
        return projectMediaService.deleteInBatch(idsList);
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
        return projectMediaService.upload(file, request);
    }

    /**
     * @param session
     * @param projectName
     * @param authStatus
     * @return 根据 项目名称、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    @RequestMapping(value = "/findByProjectNameAndAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VProjectMedia> findByProjectNameAndAuthKeyAndStatus(HttpSession session, @RequestParam("projectName") String projectName, @RequestParam("authStatus") String authStatus) {
        return vProjectMediaService.findByProjectNameAndAuthKeyAndStatus(session, projectName, authStatus);
    }

    /**
     * @param mediaTypeId
     * @return 根据多媒体类型id查询该类型所有的多媒体信息列表
     */
    @RequestMapping("/findByMediaTypeId/{mediaTypeId}")
    @ResponseBody
    public List<ProjectMedia> findByMediaTypeId(@PathVariable("mediaTypeId") Long mediaTypeId) {
        return projectMediaService.findByMediaTypesId(mediaTypeId);
    }

    /**
     * @param areaProjectId
     * @param mediaTypeId
     * @return 根据项目id、多媒体类型id以及数据权限 查询该属于该项目的该多媒体类型的信息列表
     */
    @RequestMapping(value = "/findByAreaProjectIdAndMediaTypeId", method = RequestMethod.POST)
    @ResponseBody
    public List<ProjectMedia> findByAreaProjectIdAndMediaTypeIdAndAuthKey(@RequestParam(value = "areaProjectId") Long areaProjectId,
                                                                          @RequestParam(value = "mediaTypeId") Long mediaTypeId, HttpSession session) {
        return projectMediaService.findByAreaProjectIdAndMediaTypesIdAndAuthKey(areaProjectId, mediaTypeId, session);
    }

    /**
     * @param id
     * @return 根据id进行多媒体文件授权
     */
    @RequestMapping(value = "/authorizeById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ReturnObject authorizeById(@PathVariable(value = "id") Long id) {
        return projectMediaService.authorizeById(id);
    }

    /**
     * @param idsList
     * @return 根据ids列表对多媒体文件进行批量授权
     */
    @RequestMapping(value = "/authorizeInBatch", method = RequestMethod.POST)
    @ResponseBody
    public List<ReturnObject> authorizeInBatch(@RequestParam("idsList") List<Long> idsList) {
        return projectMediaService.authorizeInBatch(idsList);
    }


    /**
     * @param session
     * @param mediaTreeCode
     * @param authStatus
     * @return 根据 mediaTreeCode、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    @RequestMapping(value = "/findByTreeNodeCodeAndAuthKeyAndStatus", method = RequestMethod.POST)
    @ResponseBody
    public List<VProjectMedia> findByTreeNodeCodeAndAuthKeyAndStatus(HttpSession session, @RequestParam("treeNodeCode") String mediaTreeCode, @RequestParam("authStatus") String authStatus) {
        return vProjectMediaService.findByTreeNodeCodeAndAuthKeyAndStatus(session, mediaTreeCode, authStatus);
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
    public Archives uploadFileAndSavePath(@RequestParam("file") MultipartFile file, @RequestParam("treeNodeId") Long treeNodeId, @RequestParam("projectId") Long projectId,
                                          HttpServletRequest request, HttpSession session, @RequestParam("userName") String userName) throws Exception {
        return projectMediaService.uploadFileAndSavePath(file, treeNodeId, projectId, request, session, userName);
    }


    /**
     * @param projectId
     * @return 根据项目id、多媒体类型id查询图片
     */
    @RequestMapping(value = "/findPicsByProjectIdAndMediaTypeId", method = RequestMethod.POST)
    @ResponseBody
    public List<ProjectMedia> findPicsByAreaId(@RequestParam(value = "projectId") Long projectId, @RequestParam(value = "mediaTypeId", defaultValue = "2l") Long mediaTypeId) {
        return projectMediaService.findPicsByProjectIdAndMediaTypeId(projectId, mediaTypeId);
    }

}
