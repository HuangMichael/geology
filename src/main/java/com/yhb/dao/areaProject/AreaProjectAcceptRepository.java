package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProjectAccept;
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
public interface AreaProjectAcceptRepository extends JpaRepository<AreaProjectAccept, Long> {

    /**
     * @param authKey
     * @param status
     * @param selectedIds
     * @return
     */
    List<AreaProjectAccept> findByProject_AuthKeyStartsWithAndStatusContainsAndIdIn(String authKey, String status, List<Long> selectedIds);

    /**
     * @param authKey
     * @param status
     * @param pageable
     * @return
     */
    Page<AreaProjectAccept> findByProject_AuthKeyStartsWithAndStatusContains(String authKey, String status, Pageable pageable);
//    /**
//     * @param projectName
//     * @return
//     */
//    List<AreaProjectAccept> findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
//    (String authKey, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);
//
//    /**
//     * @param projectName
//     * @param pageable    可分页参数
//     * @return
//     */
//    Page<AreaProjectAccept> findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
//    (String authKey, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaProjectAccept a ")
    List<Long> selectAllIds();

    /**
     * @param areaId
     * @return 根据区块id查询区块信息
     */
    List<AreaProjectAccept> findById(Long areaId);//List<Area> getOne(Long areaId)

}
