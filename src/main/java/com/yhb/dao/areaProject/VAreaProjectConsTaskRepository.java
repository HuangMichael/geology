package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VAreaProjectConsTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public interface VAreaProjectConsTaskRepository extends JpaRepository<VAreaProjectConsTask, Long> {

    /**
     * @param projectName
     * @return
     */
    List<VAreaProjectConsTask> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);

    /**
     * @param projectName
     * @param pageable    可分页参数
     * @return
     */
    Page<VAreaProjectConsTask> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VAreaProjectConsTask a ")
    List<Long> selectAllIds();

}
