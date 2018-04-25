package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VProjectMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */
public interface VProjectMediaRepository extends JpaRepository<VProjectMedia, Long> {

    /**
     * @param authKey
     * @param projectName
     * @param fileName
     * @param userName
     * @param status
     * @return 按照除去上传日期之外的查询条件复合查询
     */
    List<VProjectMedia> findByAuthKeyStartsWithAndProjectNameContainsAndFileNameContainsAndUserNameContainsAndUploadDateGreaterThanEqualAndUploadDateLessThanEqualAndStatusContains
    (String authKey, String projectName, String fileName, String userName, Date uploadDateStart, Date uploadDateEnd, String status);

    /**
     * @param authKey
     * @param projectName
     * @param status
     * @return 根据 项目名称、数据权限、审核状态 查询该项目所有的多媒体信息列表
     */
    List<VProjectMedia> findByAuthKeyStartsWithAndProjectNameContainsAndStatusContains(String authKey, String projectName, String status);


    /**
     * @param authKey
     * @param mediaTreeCode
     * @param status
     * @return 根据 数据权限、树节点编码、审核状态 查询该项目所有的多媒体信息列表
     */
    List<VProjectMedia> findByAuthKeyStartsWithAndTreeNodeCodeStartsWithAndStatusContains(String authKey, String mediaTreeCode, String status);


}
