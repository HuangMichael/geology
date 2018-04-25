package com.yhb.dao.beach;

import com.yhb.domain.beach.VBeach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
public interface VBeachRepository  extends JpaRepository<VBeach, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VBeach a ")
    List<Long> selectAllIds();

    /**
     * @return 查询所有滩涂
     */
    List<VBeach> findAll();

    List<VBeach>  findByBeachNoContainsAndBeachNameContainsAndIdIn(String beachNo,String beachName,List<Long> selectedIds);

    Page<VBeach> findByBeachNoContainsAndBeachNameContains(String beachNo,String beachName, Pageable pageable);

}
