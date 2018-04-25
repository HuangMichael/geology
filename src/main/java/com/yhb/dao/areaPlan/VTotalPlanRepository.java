package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.VTotalPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public interface VTotalPlanRepository extends JpaRepository<VTotalPlan, Long> {

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VTotalPlan a ")
    List<Long> selectAllIds();

    /**
     * @param areaNo
     * @param areaName
     * @param cityName
     * @param districtName
     * @param beginYear
     * @param endYear
     * @return
     */
    List<VTotalPlan> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdIn
    (String authKey, String cityName, String districtName, String areaNo, String areaName, Date beginYear, Date endYear, String status, List<Long> selectedIds);

    /**
     * @param authKey
     * @param cityName
     * @param districtName
     * @param areaNo
     * @param areaName
     * @param beginYear
     * @param endYear
     * @return
     */
    List<VTotalPlan> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
    (String authKey, String cityName, String districtName, String areaNo, String areaName, Date beginYear, Date endYear, String status);

    /**
     * @param areaNo
     * @param areaName
     * @param cityName
     * @param districtName
     * @param beginYear
     * @param endYear
     * @param pageable     可分页参数
     * @return
     */
    Page<VTotalPlan> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
    (String authKey, String cityName, String districtName, String areaNo, String areaName, Date beginYear, Date endYear, String status, Pageable pageable);


    /**
     * @return 查询某区块的规划的用地类型统计数据求和，并按照区块编号分组
     */
    @Query("select areaNo, sum(vtp.nyBuildSize) as nyBuildSize,sum(vtp.jsBuildSize) as jsBuildSize,sum(vtp.stBuildSize) as stBuildSize from VTotalPlan vtp where vtp.id in :idList group by areaNo order by areaNo")
    List<Object> findAreaTypeSizeGroupByAreaNo(@Param("idList") List<Long> idList);

}
