package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VAreaProjectReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public interface VAreaProjectReportRepository extends JpaRepository<VAreaProjectReport, Long> {

    /**
     * @param projectName 项目名称
     * @return 根据项目名称查询项目可行性研究报告
     */
    List<VAreaProjectReport> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);


    /**
     * @param projectName 项目名称
     * @param pageable    分页
     * @return 根据项目名称查询项目可行性研究报告
     */
    Page<VAreaProjectReport> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VAreaProjectReport a ")
    List<Long> selectAllIds();
}
