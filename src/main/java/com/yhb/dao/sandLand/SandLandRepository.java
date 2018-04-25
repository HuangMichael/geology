package com.yhb.dao.sandLand;

import com.yhb.domain.sandLand.SandLand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
public interface SandLandRepository extends JpaRepository<SandLand, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from SandLand a ")
    List<Long> selectAllIds();


    /**
     * @return 查询所有滩涂
     */
    List<SandLand> findAll();

    /**
     * @param sandNo
     * @param sandName
     * @param selectedIds
     * @return
     */
    List<SandLand>  findBySandNoContainsAndSandNameContainsAndIdIn(String sandNo, String sandName, List<Long> selectedIds);

    /**
     * @param sandNo
     * @param sandName
     * @param pageable
     * @return
     */
    Page<SandLand> findBySandNoContainsAndSandNameContains(String sandNo, String sandName, Pageable pageable);




    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from SandLand  b")
    Long findMaxId();

}
