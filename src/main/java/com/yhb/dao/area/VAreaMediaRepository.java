package com.yhb.dao.area;

import com.yhb.domain.area.VAreaMedia;
import com.yhb.domain.areaProject.VProjectMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
public interface VAreaMediaRepository extends JpaRepository<VAreaMedia, Long> {
    /**
     * @param authKey
     * @param areaName
     * @param fileName
     * @param userName
     * @param status
     * @return 按照除去上传日期之外的查询条件复合查询
     */
    List<VAreaMedia> findByAuthKeyStartsWithAndAreaNameContainsAndFileNameContainsAndUserNameContainsAndUploadDateGreaterThanEqualAndUploadDateLessThanEqualAndStatusContains
    (String authKey, String areaName, String fileName, String userName, Date uploadDateStart, Date uploadDateEnd, String status);

    /**
     * @param authKey
     * @param areaName
     * @param status
     * @return 根据 区块名称、数据权限、审核状态 查询该区块所有的多媒体信息列表
     */
    List<VAreaMedia> findByAuthKeyStartsWithAndAreaNameContainsAndStatusContains
    (String authKey, String areaName, String status);


    /**
     * @param authKey
     * @param mediaTreeCode
     * @param status
     * @return 根据 项目名称、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    List<VAreaMedia> findByAuthKeyStartsWithAndTreeNodeCodeStartsWithAndStatusContains(String authKey, String mediaTreeCode, String status);
}
