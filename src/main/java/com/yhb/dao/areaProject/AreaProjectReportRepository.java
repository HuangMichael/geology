package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProjectReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2017/5/25 0011.
 * 项目可行性研究报告信息
 */
public interface AreaProjectReportRepository extends JpaRepository<AreaProjectReport, Long> {


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaProjectReport a ")
    List<Long> selectAllIds();


    /**
     * @param projectName 项目名称
     * @return 根据项目名称查询项目可行性研究报告
     */
    List<AreaProjectReport> findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
    (String authKey, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);


    /**
     * @param projectName 项目名称
     * @param pageable    分页
     * @return 根据项目名称查询项目可行性研究报告
     */
    Page<AreaProjectReport> findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
    (String authKey, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

}
