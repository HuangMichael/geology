package com.yhb.service.media;

import com.yhb.dao.remoteSensorMedia.VRemoteSensorMediaRepository;
import com.yhb.domain.area.VAreaMedia;
import com.yhb.domain.remoteSensorMedia.VRemoteSensorMedia;
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
public class VRemoteSensorMediaService extends BaseService {
    @Autowired
    VRemoteSensorMediaRepository vRemoteSensorMediaRepository;

    /**
     * @return 根据 位置编码、数据权限、审核状态 查询该位置下各级所有的多媒体信息列表
     */
    public List<VRemoteSensorMedia> findByLocationCodeAndAuthKeyAndStatus(HttpSession session, String locationCode, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vRemoteSensorMediaRepository.findByAuthKeyStartsWithAndLocationCodeStartsWithAndStatusContains(authKey, locationCode, status);
    }



    /**
     * @return 根据 多媒体目录编码、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    public List<VRemoteSensorMedia> findByTreeNodeCodeAndAuthKeyAndStatus(HttpSession session, String mediaTreeCode, String status) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vRemoteSensorMediaRepository.findByAuthKeyStartsWithAndTreeNodeCodeStartsWithAndStatusContains(authKey, mediaTreeCode, status);
    }


}
