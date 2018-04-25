package com.yhb.dao.areaProject;

import com.yhb.domain.areaProject.AreaProjectConsTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public interface AreaProjectConsTaskRepository extends JpaRepository<AreaProjectConsTask, Long> {


    /**
     * @param projectName
     * @return
     */
    List<AreaProjectConsTask> findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContainsAndIdIn
    (String authKey, String projectName, Date beginDate, Date endDate, String status, List<Long> selectedIds);


    /**
     * @param projectName
     * @param pageable    可分页参数
     * @return
     */
    Page<AreaProjectConsTask> findByProject_AuthKeyStartsWithAndProject_ProjectNameContainsAndBeginDateGreaterThanEqualAndEndDateLessThanEqualAndStatusContains
    (String authKey, String projectName, Date beginDate, Date endDate, String status, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaProjectConsTask a ")
    List<Long> selectAllIds();
}
