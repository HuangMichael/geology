package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.ThirteenFivePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface ThirteenFivePlanRepository extends JpaRepository<ThirteenFivePlan, Long> {
    /**
     * @param areaId
     * @return
     */
    ThirteenFivePlan findByAreaId(Long areaId);

    /**
     * @param planDesc
     * @return
     */
    List<ThirteenFivePlan> findByPlanDescContains(String planDesc);
    /**
     * @param planDesc
     * @param pageable 可分页参数
     * @return
     */
    Page<ThirteenFivePlan> findByPlanDescContains(String planDesc, Pageable pageable);



    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    @Query("select tfp.area.id, tfp.nyBuildSize,tfp.jsBuildSize,tfp.stBuildSize from ThirteenFivePlan tfp where tfp.area.id =:areaId")
    List<Object> findAreaTypeSizeByAreaId(@Param("areaId") Long areaId);

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据求和，依次代表农业、建设、生态三种用地类型的总和
     */
    @Query("select sum(tfp.nyBuildSize),sum(tfp.jsBuildSize),sum(tfp.stBuildSize) from ThirteenFivePlan tfp where tfp.area.id =:areaId")
    Object findAreaTypeSizeTotalByAreaId(@Param("areaId") Long areaId);



    /**
     * @return 查询最大的id
     */
    @Query("select max(b.id)+1 from ThirteenFivePlan  b")
    Long findMaxId();


}
