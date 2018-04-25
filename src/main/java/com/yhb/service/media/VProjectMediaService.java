package com.yhb.service.media;

import com.yhb.dao.areaProject.VProjectMediaRepository;
import com.yhb.domain.areaProject.VProjectMedia;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.DataFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
@Service
public class VProjectMediaService extends BaseService {
    @Autowired
    VProjectMediaRepository vProjectMediaRepository;

    /**
     * @return 根据 项目名称、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    public List<VProjectMedia> findByProjectNameAndAuthKeyAndStatus(HttpSession session, String projectName, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vProjectMediaRepository.findByAuthKeyStartsWithAndProjectNameContainsAndStatusContains(authKey, projectName, status);
    }


    /**
     * @return 根据 多媒体目录编码、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    public List<VProjectMedia> findByTreeNodeCodeAndAuthKeyAndStatus(HttpSession session, String mediaTreeCode, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vProjectMediaRepository.findByAuthKeyStartsWithAndTreeNodeCodeStartsWithAndStatusContains(authKey, mediaTreeCode, status);
    }

}
