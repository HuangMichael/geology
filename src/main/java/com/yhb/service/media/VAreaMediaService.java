package com.yhb.service.media;

import com.yhb.dao.area.VAreaMediaRepository;
import com.yhb.domain.area.VAreaMedia;
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
public class VAreaMediaService extends BaseService {
    @Autowired
    VAreaMediaRepository vAreaMediaRepository;

    /**
     * @return 根据 项目名称、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    public List<VAreaMedia> findByAreaNameAndAuthKeyAndStatus(HttpSession session, String areaName, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vAreaMediaRepository.findByAuthKeyStartsWithAndAreaNameContainsAndStatusContains(authKey, areaName, status);
    }



    /**
     * @return 根据 多媒体目录编码、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    public List<VAreaMedia> findByTreeNodeCodeAndAuthKeyAndStatus(HttpSession session, String mediaTreeCode, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vAreaMediaRepository.findByAuthKeyStartsWithAndTreeNodeCodeStartsWithAndStatusContains(authKey, mediaTreeCode, status);
    }




}
