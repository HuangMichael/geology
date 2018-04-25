package com.yhb.service.mediaTree;

import com.yhb.dao.areaProject.ProjectMediaRepository;
import com.yhb.dao.mediaTree.MediaTreeRepository;
import com.yhb.domain.areaProject.ProjectMedia;
import com.yhb.domain.mediaCatalog.MediaTree;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by huangbin on 2017/10/20 0004.
 * 多媒体树业务类
 */
@Service
public class MediaTreeService extends BaseService {

    @Autowired
    MediaTreeRepository mediaTreeRepository;

    @Autowired
    CommonDataService commonDataService;


    @Autowired
    ProjectMediaRepository projectMediaRepository;

    /**
     * @param mediaTree
     * @return 保存多媒体树信息
     */
    public MediaTree save(MediaTree mediaTree) {
        return mediaTreeRepository.save(mediaTree);
    }

    /**
     * @param id 根据ID删除多媒体树
     */
    public ReturnObject delete(Long id) {
        MediaTree mediaTree = mediaTreeRepository.findOne(id);
        if (mediaTree == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的多媒体树不存在,请确认！");
        }
        try {
            mediaTreeRepository.delete(mediaTree);
            //再查询一次查看是否删除
            MediaTree mediaTree1 = mediaTreeRepository.findOne(id);
            return commonDataService.getReturnType(mediaTree1 == null, "多媒体树删除成功！", "多媒体树删除失败！");
        } catch (EntityNotFoundException e) {
            return commonDataService.getReturnType(true, "多媒体树删除成功！", "多媒体树删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public MediaTree findById(Long id) {
        return mediaTreeRepository.findOne(id);

    }

    /**
     * @return 根据多媒体树类型查询多媒体树信息
     */
    public List<MediaTree> findByTreeType(HttpSession session, String mediaType) {

        String authKey = DataFilterUtils.getAuthKey(session);
        return mediaTreeRepository.findByTreeTypeAndAuthKeyStartingWithAndStatus(mediaType, authKey, "1");

    }


    /**
     * @return 选择所有的id列表
     */
    public List<Long> selectAllIds() {
        return mediaTreeRepository.selectAllIds();
    }


    /**
     * @return 选择所有的id列表
     */
    public Long findMaxIdByPid(Long pid) {
        return mediaTreeRepository.findMaxId(pid);
    }


    /**
     * @param nodeId 多媒体树节点
     * @return 根据多媒体节点查询文档数目
     */
    public Integer getDocsNumByTreeNodeid(Long nodeId) {
        List<ProjectMedia> mediaList;
        MediaTree mediaTree = mediaTreeRepository.findOne(nodeId);
        mediaList = projectMediaRepository.findByMediaTree(mediaTree);
        return mediaList.size();
    }

}
