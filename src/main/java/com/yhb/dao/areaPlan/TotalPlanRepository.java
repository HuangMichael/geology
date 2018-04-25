package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.TotalPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public interface TotalPlanRepository extends JpaRepository<TotalPlan, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select t.id from TotalPlan t ")
    List<Long> selectAllIds();

    /**
     * @param areaId
     * @return
     */
    List<TotalPlan> findByAreaId(Long areaId);


    /**
     * @param id
     * @return
     */
    TotalPlan findById(Long id);


    /**
     * @param planDesc
     * @return
     */
    List<TotalPlan> findByPlanDescContains(String planDesc);
    /**
     * @param planDesc
     * @param pagetple 可分页参数
     * @return
     */
    Page<TotalPlan> findByPlanDescContains(String planDesc,Pageable pagetple);



    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    @Query("select tp.area.id, tp.nyBuildSize,tp.jsBuildSize,tp.stBuildSize from TotalPlan tp where tp.area.id =:areaId")
    List<Object> findAreaTypeSizeByAreaId(@Param("areaId") Long areaId);

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据求和，依次代表农业、建设、生态三种用地类型的总和
     */
    @Query("select sum(tp.nyBuildSize) as nyBuildSize,sum(tp.jsBuildSize) as jsBuildSize,sum(tp.stBuildSize) as stBuildSize from TotalPlan tp where tp.area.id =:areaId")
    Object findAreaTypeSizeTotalByAreaId(@Param("areaId") Long areaId);


    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from TotalPlan  b")
    Long findMaxId();

}
