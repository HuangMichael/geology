package com.yhb.dao.devUsing;


import com.yhb.domain.areaDevUsing.AreaDevUsing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by llm on 2017/5/16 0016.
 * 开发利用数据接口
 */
public interface AreaDevUsingRepository extends JpaRepository<AreaDevUsing, Long> {
    /**
     * @param title 标题
     * @return 根据标题和用户选择的id列表查询开发利用信息
     */
    List<AreaDevUsing> findByTitleContainsAndIdIn(String title,List<Long> selectedIds);

    /**
     * @param title 标题
     * @return 根据标题查询开发利用信息
     */
    List<AreaDevUsing> findByTitleContains(String title);


    /**
     * @param title    标题
     * @param pageable
     * @return 根据标题查询开发利用信息
     */
    Page<AreaDevUsing> findByTitleContains(String title, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from AreaDevUsing a ")
    List<Long> selectAllIds();

    /**
     * @param level 级别
     * @return 根据级别查询开发利用信息列表
     */
    List<AreaDevUsing> findByLevelOrderBySortNo(Long level);


    /**
     * @param pId 父级id
     * @return 根据pId查询所有符合的开发利用信息列表
     */
    List<AreaDevUsing> findByParentIdOrderBySortNo(Long pId);

    /**
     * @param pId 父id
     * @return 根据父id查询孩子节点中最大的sortNo，返回最大的排序值maxSortNo
     */
    @Query("select max(a.sortNo) as maxSortNo from AreaDevUsing a where a.parent.id = :pId")
    Long findMaxSortNoByPId(@Param("pId")Long pId);

    /**
     * @param level 级别
     * @return 根据级别查询同级别里最大的sortNo，返回最大的排序值maxSortNo
     */
    @Query("select max(a.sortNo) as maxSortNo from AreaDevUsing a where a.level = :level")
    Long findMaxSortNoByLevel(@Param("level") Long level);
}