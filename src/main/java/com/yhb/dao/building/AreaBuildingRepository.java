package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.AreaBuilding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by huangbin on 2017/5/8 0008.
 * 围垦信息业务类
 */
public interface AreaBuildingRepository extends JpaRepository<AreaBuilding, Long> {

    /**
     * @param areaId 区块id
     * @return 根据区块id查询该区块的现状描述信息
     */
    @Query("select ab.area.id,ab.buildDesc from AreaBuilding ab where ab.area.id=:areaId")
    List<Object> findAreaDescById(@Param("areaId") Long areaId);


    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    @Query("select ab.area.id, ab.nyBuildSize,ab.jsBuildSize,ab.stBuildSize from AreaBuilding ab where ab.area.id=:areaId")
    List<Object> findAreaTypeSizeByAreaId(@Param("areaId") Long areaId);

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据求和，依次代表农业、建设、生态三种用地类型的总和
     */
    @Query("select sum(ab.nyBuildSize),sum(ab.jsBuildSize),sum(ab.stBuildSize) from AreaBuilding ab where ab.area.id =:areaId")
    Object findAreaTypeSizeTotalByAreaId(@Param("areaId") Long areaId);

    /**
     * @return 查询所有围垦信息
     */
    List<AreaBuilding> findAll();


    /**
     * @param pageable 可分页
     * @return 查询所有围垦信息
     */
    Page<AreaBuilding> findAll(Pageable pageable);

    /**
     * @param areaId
     * @return
     */
    List<AreaBuilding> findByAreaId(Long areaId);





    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from AreaBuilding  b")
    Long findMaxId();

}
