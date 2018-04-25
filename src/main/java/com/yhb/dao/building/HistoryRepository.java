package com.yhb.dao.building;


import com.yhb.domain.history.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by llm on 2017/5/16 0016.
 * 历史沿革数据接口
 */
public interface HistoryRepository extends JpaRepository<History, Long> {

    /**
     * @param title 标题
     * @return 根据标题和用户选择的id列表查询历史沿革信息
     */
    List<History> findByTitleContainsAndIdIn(String title, List<Long> selectedIds);

    /**
     * @param title 标题
     * @return 根据标题查询历史沿革信息
     */
    List<History> findByTitleContains(String title);

    /**
     * @param title    标题
     * @param pageable
     * @return 根据标题查询历史沿革信息
     */
    Page<History> findByTitleContains(String title, Pageable pageable);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from History a ")
    List<Long> selectAllIds();


    /**
     * @param level 级别
     * @return 根据级别查询历史沿革信息列表
     */
    List<History> findByLevelOrderBySortNo(Long level);


    /**
     * @param pId 父级id
     * @return 根据pId查询所有符合的历史沿革信息列表
     */
    List<History> findByParentIdOrderBySortNo(Long pId);

    /**
     * @param pId 父id
     * @return 根据父id查询孩子节点中最大的sortNo，返回最大的排序值maxSortNo
     */
    @Query("select max(h.sortNo) as maxSortNo from History h where h.parent.id = :pId")
    Long findMaxSortNoByPId(@Param("pId")Long pId);

    /**
     * @param level 级别
     * @return 根据级别查询同级别里最大的sortNo，返回最大的排序值maxSortNo
     */
    @Query("select max(h.sortNo) as maxSortNo from History h where h.level = :level")
    Long findMaxSortNoByLevel(@Param("level") Long level);
}