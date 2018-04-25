package com.yhb.dao.farmLand;

import com.yhb.domain.farmLand.FarmLand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/20 0004.
 * 耕地数据接口
 */
public interface FarmLandRepository extends JpaRepository<FarmLand, Long> {
    /**
     * @param landNo
     * @param landName
     * @param pageable 可分页参数
     * @return
     */
    Page<FarmLand> findByLandNoContainsAndLandNameContains(String landNo, String landName, Pageable pageable);


    /**
     * @param landNo
     * @param landName
     * @return
     */
    List<FarmLand> findByLandNoContainsAndLandNameContainsAndIdIn(String landNo, String landName, List<Long> selectedIds);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from FarmLand a ")
    List<Long> selectAllIds();


    /**
     * @return 查询所有海岸线
     */
    List<FarmLand> findAll();


    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from FarmLand  b")
    Long findMaxId();

}



