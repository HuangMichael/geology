package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2017/5/23 0004.
 * 项目信息数据接口
 */
public interface AreaProjectRepository extends JpaRepository<AreaProject, Long>, PagingAndSortingRepository<AreaProject, Long> {
    /**
     * @param projectName 项目名称
     * @return
     */
    List<AreaProject> findByAuthKeyStartsWithAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdIn
    (String authKey, String projectNo, String projectName, Date beginYear, Date endYear, String status, List<Long> selectedIds);


    /**
     * @param projectName 项目名称
     * @return
     */
    Page<AreaProject> findByAuthKeyStartsWithAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
    (String authKey, String projectNo, String projectName, Date beginYear, Date endYear, String status, Pageable pageable);

    /**
     * @param projectName 项目名称
     * @return
     */
    List<AreaProject> findByAuthKeyStartsWithAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
    (String authKey, String projectNo, String projectName, Date beginYear, Date endYear, String status);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaProject a ")
    List<Long> selectAllIds();

    /**
     * @return 获取所有项目的 预算、已投资额 信息
     */
    @Query("select id,projectNo,projectName,budget,investedSum from AreaProject ap order by id")
    List<Object> getAllProjectFund();

    /**
     * @param idList
     * @return 获取符合查询条件的项目的 预算、已投资额 信息
     */
    @Query("select id,projectNo,projectName,budget,investedSum from AreaProject ap where id in :idList order by id")
    List<Object> getProjectFundByIds(@Param("idList") List<Long> idList);

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    @Query("select a.id from AreaProject a where a.id<>:id")
    List<Long> selectOtherIds(@Param("id") Long id);

    /**
     * @param projectNo
     * @return
     */
    AreaProject findByProjectNo(String projectNo);

    /**
     * @param projectNo
     * @param otherIds
     * @return 编辑项目信息时，根据前端传过来的用户输入的新项目编号和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    AreaProject findByProjectNoAndIdIn(String projectNo, List<Long> otherIds);

    /**
     * @param projectName
     * @return
     */
    AreaProject findByProjectName(String projectName);


    /**
     * @param projectName
     * @param otherIds
     * @return 编辑项目信息时，根据前端传过来的用户输入的新项目名称和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    AreaProject findByProjectNameAndIdIn(String projectName, List<Long> otherIds);

    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from AreaProject  b")
    Long findMaxId();


    /**
     * @param buildStatus 项目建设状态
     * @return 根据项目建设状态查询项目信息
     */
    List<AreaProject> findByBuildStatus(String buildStatus);

    /**
     * @param authKey
     * @return 根据授权码查询项目信息
     */
    List<AreaProject> findByAuthKeyStartingWith(String authKey);

    /**
     * @param authKey
     * @return 根据授权码和审核状态查询项目信息
     */
    List<AreaProject> findByAuthKeyStartsWithAndStatusContains(String authKey, String status);

    /**
     * @return 根据项目重要性排序，先显示重要项目
     */
    @Query("select a from AreaProject a order by a.important desc")
    List<AreaProject> findAllOrderByImportantDesc();


    /**
     * @return 按照项目建设状态查询   0为规划  1 为在建  2 为已建
     */
    @Query("select a from AreaProject a where a.buildStatus =:buildStatus order by a.important desc")
    List<AreaProject> findByBuildStatusOrderByImportantDesc(@Param("buildStatus") String buildStatus);

    /**
     * @return 按照项目建设状态(0为规划 1 为在建 2 为已建)和位置id查询
     */
    @Query("select a from AreaProject a where a.buildStatus =:buildStatus and (a.city.id = :locId or a.district.id = :locId) order by a.important desc")
    List<AreaProject> findByBuildStatusAndLocIdOrderByImportantDesc(@Param("buildStatus") String buildStatus, @Param("locId") Long locId);

    /**
     * @return 按照项目建设状态查询   0为规划  1 为在建  2 为已建
     */
    @Query("select a from AreaProject a where a.buildStatus <>:buildStatus order by a.important desc")
    List<AreaProject> findByBuildStatusNotOrderByImportantDesc(@Param("buildStatus") String buildStatus);

    /**
     * @return 按照项目建设状态(0为规划 1 为在建 2 为已建)和位置id查询
     */
    @Query("select a from AreaProject a where a.buildStatus <>:buildStatus and (a.city.id = :locId or a.district.id = :locId) order by a.important desc")
    List<AreaProject> findByBuildStatusNotAndLocIdOrderByImportantDesc(@Param("buildStatus") String buildStatus, @Param("locId") Long locId);
}
