package com.yhb.dao.areaPlan;

import com.yhb.domain.areaPlan.VThirteenFivePlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */
public interface VThirteenFivePlanRepository extends JpaRepository<VThirteenFivePlan, Long> {
    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VThirteenFivePlan a ")
    List<Long> selectAllIds();

    /**
     * @param authKey
     * @param cityName
     * @param districtName
     * @param areaNo
     * @param areaName
     * @param selectedIds
     * @return
     */
    List<VThirteenFivePlan> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContainsAndIdIn
    (String authKey, String cityName, String districtName, String areaNo, String areaName, String status, List<Long> selectedIds);

    /**
     * @param areaNo
     * @param areaName
     * @param cityName
     * @param districtName
     * @param pageable     可分页参数
     * @return
     */
    Page<VThirteenFivePlan> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains
    (String authKey, String cityName, String districtName, String areaNo, String areaName, String status, Pageable pageable);


    /**
     * @param authKey
     * @param cityName
     * @param districtName
     * @param areaNo
     * @param areaName
     * @return
     */
    List<VThirteenFivePlan> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains
    (String authKey, String cityName, String districtName, String areaNo, String areaName, String status);

    /**
     * @return 查询某区块的规划的用地类型统计数据求和，并按照区块编号分组
     */
    @Query("select areaNo, sum(vtp.nyBuildSize) as nyBuildSize,sum(vtp.jsBuildSize) as jsBuildSize,sum(vtp.stBuildSize) as stBuildSize from VThirteenFivePlan vtp where vtp.id in :idList group by areaNo order by areaNo")
    List<Object> findAreaTypeSizeGroupByAreaNo(@Param("idList") List<Long> idList);
}
