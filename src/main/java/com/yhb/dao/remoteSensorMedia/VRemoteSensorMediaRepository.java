package com.yhb.dao.remoteSensorMedia;

import com.yhb.domain.area.VAreaMedia;
import com.yhb.domain.remoteSensorMedia.VRemoteSensorMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
public interface VRemoteSensorMediaRepository extends JpaRepository<VRemoteSensorMedia, Long> {
    /**
     * @param authKey
     * @param locationCode
     * @param fileName
     * @param userName
     * @param uploadDateStart
     * @param uploadDateEnd
     * @param status
     * @return 按照除去上传日期之外的查询条件复合查询
     */
    List<VRemoteSensorMedia> findByAuthKeyStartsWithAndLocationCodeStartsWithAndFileNameContainsAndUserNameContainsAndUploadDateGreaterThanEqualAndUploadDateLessThanEqualAndStatusContains
    (String authKey, String locationCode, String fileName, String userName, Date uploadDateStart, Date uploadDateEnd, String status);

    /**
     * @param authKey
     * @param locationCode
     * @param status
     * @return 根据 位置名称、数据权限、审核状态 查询该位置的所有的多媒体信息列表
     */
    List<VRemoteSensorMedia> findByAuthKeyStartsWithAndLocationCodeStartsWithAndStatusContains
    (String authKey, String locationCode, String status);

    /**
     * @param authKey
     * @param mediaTreeCode
     * @param status
     * @return 根据 项目名称、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    List<VRemoteSensorMedia> findByAuthKeyStartsWithAndTreeNodeCodeStartsWithAndStatusContains(String authKey, String mediaTreeCode, String status);
}
