package com.yhb.dao.location;

import com.yhb.domain.location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public interface LocationRepository extends JpaRepository<Location, Long>, CrudRepository<Location, Long> {

    /**
     * 按照位置名称查询
     *
     * @param locName 位置名称
     * @return
     */
    Location findByLocName(String locName);

    /**
     * @param locCode 根据locCode查询
     * @return
     */
    Location findByLocCode(String locCode);

    List<Location> findLocsByParent_ParentId(Long pId);

    List<Location> findByParentIdOrderByLocCodeAsc(Long pId);

    /**
     * @param locCode 位置编码
     * @return
     */
    List<Location> findByLocCodeStartsWith(String locCode);

    /**
     * @param locName
     * @return
     */
    List<Location> findByLocNameContainsAndIdIn(String locName, List<Long> selectedIds);

    /**
     * @param locName
     * @param pageable 可分页参数
     * @return
     */
    Page<Location> findByLocNameContains(String locName, Pageable pageable);


    /**
     * @param level
     * @return 查询所有的位置级别小于level的位置列表
     */
    List<Location> findByLocLevelLessThan(Long level);

    /**
     * @param level
     * @return 查询位置级别等于该level的所有位置列表
     */
    List<Location> findByLocLevel(Long level);
}
