package com.yhb.dao.sandLand;

import com.yhb.domain.sandLand.VSandLand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
public interface VSandLandRepository extends JpaRepository<VSandLand, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VSandLand a ")
    List<Long> selectAllIds();

    /**
     * @param sandNo
     * @param sandName
     * @param selectedIds
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    List<VSandLand> findBySandNoContainsAndSandNameContainsAndIdIn(String sandNo, String sandName, List<Long> selectedIds);

    /**
     * @param sandNo
     * @param sandName
     * @param pageable
     * @return 根据多条件复合查询沿海沙洲视图信息
     */
    Page<VSandLand> findBySandNoContainsAndSandNameContains(String sandNo, String sandName, Pageable pageable);

}
