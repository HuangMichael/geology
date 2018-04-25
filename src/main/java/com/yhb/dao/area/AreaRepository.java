package com.yhb.dao.area;

import com.yhb.domain.area.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 围垦区块数据接口
 */
public interface AreaRepository extends JpaRepository<Area, Long> {
    /**
     * @param areaNo
     * @param areaName
     * @return
     */
    List<Area> findByAuthKeyStartsWithAndAreaNoContainsAndAreaNameContains(String authKey,String areaNo, String areaName);

    /**
     * @param areaNo
     * @param areaName
     * @return
     */
    List<Area> findByAuthKeyStartsWithAndAreaNoContainsAndAreaNameContainsAndIdIn(String authKey,String areaNo, String areaName, List<Long> selectedIds);

    /**
     * @param areaNo
     * @param areaName
     * @param pageable 可分页参数
     * @return
     */
    Page<Area> findByAuthKeyStartsWithAndAreaNoContainsAndAreaNameContains(String authKey,String areaNo, String areaName, Pageable pageable);


    /**
     * @return 查询所有的id
     */
    @Query("select a.id from Area a ")
    List<Long> selectAllIds();


    /**
     * @param areaNo
     * @return 根据区块编号查询
     */
    Area findByAreaNo(String areaNo);

    /**
     * @param areaNo   
     * @param otherIds
     * @return 编辑区块信息时，根据前端传过来的用户输入的新区块编号和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    Area findByAreaNoAndIdIn(String areaNo, List<Long> otherIds);
    /**
     * @param areaName
     * @return 根据区块名称查询
     */
    Area findByAreaName(String areaName);

    /**
     * @param areaName
     * @param otherIds
     * @return 编辑区块信息时，根据前端传过来的用户输入的新区块编号和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    Area findByAreaNameAndIdIn(String areaName, List<Long> otherIds);

    /**
     * @param locationId 位置id
     * @return 根据位置id查询区块信息
     */
    @Query("select a from Area a where a.city.id=:locationId or a.district.id=:locationId order by a.areaNo")
    List<Area> findAreasByLocationId(@Param("locationId")Long locationId);

    /**
     * @param id
     * @return 根据区块id查询区块信息
     */
    Area findById(Long id);

    /**
     * @param areaNo
     * @param areaName
     * @return 根据区块编号和区块名称查询区块信息
     */
    Area findByAreaNoAndAreaName(String areaNo, String areaName);

    /**
     * @return 查询所有区块，按照区块编号排序
     */
    @Query("select a from Area a order by a.areaNo")
    List<Area> findAllOrderByAreaNo();

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    @Query("select a.id from Area a where a.id<>:id")
    List<Long> selectOtherIds(@Param("id") Long id);


}
