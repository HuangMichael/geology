package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VAreaProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public interface VAreaProjectRepository extends JpaRepository<VAreaProject, Long> {
    /**
     * @param projectName 项目名称
     * @return
     */
    List<VAreaProject> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginYear, Date endYear, String status, List<Long> selectedIds);


    /**
     * @param projectName 项目名称
     * @return
     */
    Page<VAreaProject> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginYear, Date endYear, String status, Pageable pageable);

    /**
     * @param projectName 项目名称
     * @return
     */
    List<VAreaProject> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginYear, Date endYear, String status);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaProject a ")
    List<Long> selectAllIds();

    /**
     * @param authKey
     * @return 根据授权码和审核状态查询项目信息
     */
    List<VAreaProject> findByAuthKeyStartsWithAndStatusContains(String authKey, String status);

    /**
     * @param authKey
     * @param cityName
     * @param districtName
     * @param status
     * @return 根据 授权码、市、县区、审核状态 查询项目信息
     */
    List<VAreaProject> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndStatusContains(String authKey, String cityName, String districtName, String status);
}
