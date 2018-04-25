package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProjectCons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 */
public interface AreaProjectConsRepository extends JpaRepository<AreaProjectCons, Long> {

//    /**
//     * @param projectName
//     * @return
//     */
//    List<AreaProjectCons> findByAuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
//    (String authKey, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);
//
//
//    /**
//     * @param projectName
//     * @param pageable    可分页参数
//     * @return
//     */
//    Page<AreaProjectCons> findByAuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
//    (String authKey, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @param authKey
     * @param status
     * @param selectedIds
     * @return
     */
    List<AreaProjectCons> findByProject_AuthKeyStartsWithAndStatusContainsAndIdIn(String authKey, String status, List<Long> selectedIds);


    /**
     * @param authKey
     * @param status
     * @param pageable
     * @return
     */
    Page<AreaProjectCons> findByProject_AuthKeyStartsWithAndStatusContains(String authKey, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaProjectCons a ")
    List<Long> selectAllIds();
}
