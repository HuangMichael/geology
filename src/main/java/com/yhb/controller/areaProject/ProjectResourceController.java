package com.yhb.controller.areaProject;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.areaProject.ProjectTree;
import com.yhb.service.areaProject.AreaProjectResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by huangbin on 2017/5/20 0004.
 * 项目资料信息
 */
@Controller
@RequestMapping("/projectResource")
public class ProjectResourceController extends BaseController {
    @Autowired
    AreaProjectResourceService areaProjectResourceService;

    String objectName = "项目资料";



    /**
     * 根据授权码和审核状态查询所有的项目树
     *
     * @return 所有项目位置变为ztree的形式，而且根据授权码和审核状态查询
     */
    @RequestMapping(value = "/findProjectTree", method = RequestMethod.POST)
    @ResponseBody
    public List<ProjectTree> findProjectTreeByAuthKeyAndStatus(HttpSession session, @RequestParam("authStatus") String authStatus) {
        return areaProjectResourceService.findProjectTreeByAuthKeyAndStatus(session,authStatus);
    }

}
