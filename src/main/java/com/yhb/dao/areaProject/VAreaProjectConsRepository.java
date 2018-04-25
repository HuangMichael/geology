package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VAreaProjectCons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
public interface VAreaProjectConsRepository extends JpaRepository<VAreaProjectCons, Long> {
    /**
     * @param projectName
     * @return
     */
    List<VAreaProjectCons> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);

    /**
     * @param projectName
     * @param pageable    可分页参数
     * @return
     */
    Page<VAreaProjectCons> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VAreaProjectCons a ")
    List<Long> selectAllIds();
}
