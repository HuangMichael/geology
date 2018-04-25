package com.yhb.dao.beach;

import com.yhb.domain.beach.Beach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
public interface BeachRepository extends JpaRepository<Beach, Long> {

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from Beach a ")
    List<Long> selectAllIds();


    /**
     * @return 查询所有滩涂
     */
    List<Beach> findAll();

    /**
     * @param beachNo
     * @param beachName
     * @param selectedIds
     * @return
     */
    List<Beach> findByBeachNoContainsAndBeachNameContainsAndIdIn(String beachNo, String beachName, List<Long> selectedIds);

    /**
     * @param beachNo
     * @param beachName
     * @param pageable
     * @return
     */
    Page<Beach> findByBeachNoContainsAndBeachNameContains(String beachNo, String beachName, Pageable pageable);


    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from Beach  b")
    Long findMaxId();
}
