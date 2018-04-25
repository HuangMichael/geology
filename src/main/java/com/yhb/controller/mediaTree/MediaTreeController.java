package com.yhb.controller.mediaTree;

import com.yhb.controller.common.BaseController;
import com.yhb.domain.mediaCatalog.MediaTree;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.mediaTree.MediaTreeService;
import com.yhb.utils.StringUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by huangbin on 2017/10/21 0004.
 * 遥感多媒体树控制器
 */
@Controller
@RequestMapping("/mediaTree")
@Slf4j
@Data
public class MediaTreeController extends BaseController {

    protected String treeType = "";
    @Autowired
    MediaTreeService mediaTreeService;
    @Autowired
    CommonDataService commonDataService;

    @RequestMapping(value = "/findMediaTree", method = RequestMethod.POST)
    @ResponseBody
    public List<MediaTree> findMediaTree(HttpSession session) {
        // 根据用户所在位置数据授权码和多媒体树类型查询树结构
        log.info("treeType", this.getTreeType());
        return mediaTreeService.findByTreeType(session, this.getTreeType());
    }


    /**
     * @param treeId   树节点id
     * @param nodeName 节点名称
     * @return
     */

    @RequestMapping(value = "updateNode", method = RequestMethod.POST)
    @ResponseBody
    protected ReturnObject updateNode(@RequestParam("treeId") Long treeId, @RequestParam("nodeName") String nodeName) {
        MediaTree mediaTree = mediaTreeService.findById(treeId);
        mediaTree.setTreeName(nodeName);
        mediaTree = mediaTreeService.save(mediaTree);
        return commonDataService.getReturnType(mediaTree != null, "更新成功", "更新失败");
    }


    /**
     * @param treeName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "addNode", method = RequestMethod.POST)
    @ResponseBody
    protected ReturnObject addNode(HttpSession session, @RequestParam("treeName") String treeName, @RequestParam("parentId") Long parentId) {
        MediaTree parent = mediaTreeService.findById(parentId);
        MediaTree mediaTree = new MediaTree();
        Long maxId = mediaTreeService.findMaxIdByPid(parentId) + 1;
        String code = StringUtils.genServiceCode(parent.getTreeNodeCode(), maxId + "", 2l);
        mediaTree.setTreeNodeCode(code);
        mediaTree.setTreeName(treeName);
        mediaTree.setTreeDesc(treeName);
        String authKey = DataFilterUtils.getAuthKey(session);
        mediaTree.setAuthKey(authKey);
        mediaTree.setStatus("1");
        mediaTree.setTreeType(parent.getTreeType());
        mediaTree.setParent(parent);
        mediaTreeService.save(mediaTree);
        return commonDataService.getReturnType(mediaTree != null, "新增成功", "新增失败");
    }


    @RequestMapping(value = "/findMaxIdByPid/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public Long findMaxIdByPid(@PathVariable("pid") Long pid) {
        // 根据用户所在位置数据授权码和多媒体树类型查询树结构
        return mediaTreeService.findMaxIdByPid(pid);
    }


    /**
     * @param nodeId 节点id
     * @return 返回删除结果
     */
    @RequestMapping(value = "/delNode", method = RequestMethod.POST)
    @ResponseBody
    protected ReturnObject delNode(@RequestParam("nodeId") Long nodeId) {
        ReturnObject returnObject = null;
        MediaTree node = mediaTreeService.findById(nodeId);
        Long subNodeNum = mediaTreeService.findMaxIdByPid(nodeId);
        boolean result = false;
        //查询是否有子目录
        if (subNodeNum > 0) {
            result = false;
        } else if (subNodeNum == 0) {
            //如果有子目录  查看子目录是否关联文档
            Integer filesNum = mediaTreeService.getDocsNumByTreeNodeid(nodeId);
            //查询树结构关联的多媒体目录
            if (filesNum == 0) {
                node.setStatus("0");
                node = mediaTreeService.save(node);
                result = node.getStatus().equals("0");
            } else {
                result = false;
            }
        }
        returnObject = commonDataService.getReturnType(result, "删除成功", "删除失败");
        return returnObject;
    }


}
