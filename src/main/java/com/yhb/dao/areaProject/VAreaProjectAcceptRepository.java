package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.VAreaProjectAccept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 项目验收信息接口
 */
public interface VAreaProjectAcceptRepository extends JpaRepository<VAreaProjectAccept, Long> {


    /**
     * @param projectName
     * @return
     */
    List<VAreaProjectAccept> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);


    /**
     * @param projectName
     * @param pageable    可分页参数
     * @return
     */
    Page<VAreaProjectAccept> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsOrderByImportantDesc
    (String authKey, String cityName, String districtName, String projectNo, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VAreaProjectAccept a ")
    List<Long> selectAllIds();

    /**
     * @param areaId
     * @return 根据区块id查询区块信息
     */
    List<VAreaProjectAccept> findById(Long areaId);

}
