package com.yhb.dao.log;

import com.yhb.domain.log.AppQueryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 应用查询日志数据接口
 */
public interface AppQueryLogRepository extends JpaRepository<AppQueryLog, Long> {

    /**
     * @param userName
     * @param idList
     * @return
     */
    List<AppQueryLog> findByUserNameContainsAndIdIn(String userName, List<Long> idList);


    /**
     * @param userName
     * @param pageable
     * @return
     */
    Page<AppQueryLog> findByUserNameContains(String userName, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AppQueryLog a ")
    List<Long> selectAllIds();
}