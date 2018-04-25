package com.yhb.dao.building;

import com.yhb.domain.areaBuilding.VAreaBuilding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by huangbin on 2017/5/8 0008.
 * 围垦信息统计分析接口类
 */
public interface VAreaBuildingRepository extends JpaRepository<VAreaBuilding, Long> {

    /**
     * @param areaNo 区块编号
     * @return 根据区块编号查询
     */
    List<VAreaBuilding> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdIn
    (String authKey, String cityName, String districtName, String areaNo, String areaName, Date beginYear, Date endYear, String status, List<Long> selectedIds);


    /**
     * @param areaNo 区块编号
     * @return 根据区块编号查询
     */
    Page<VAreaBuilding> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
    (String authKey, String cityName, String districtName, String areaNo, String areaName, Date beginYear, Date endYear, String status, Pageable pageable);

    /**
     * @return 查询某区块的已围垦的用地类型统计数据求和，并按照区块编号分组
     */
    @Query("select areaNo, sum(vab.nyBuildSize) as nyBuildSize,sum(vab.jsBuildSize) as jsBuildSize,sum(vab.stBuildSize) as stBuildSize from VAreaBuilding vab where vab.areaNo in :areaNoList group by areaNo order by areaNo")
    List<Object> findAreaTypeSizeGroupByAreaNo(@Param("areaNoList") List<String> areaNoList);

    /**
     * @param areaNo 区块编号
     * @return 根据区块编号查询
     */
    List<VAreaBuilding> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContains
    (String authKey, String cityName, String districtName, String areaNo, String areaName, Date beginYear, Date endYear, String status);

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VAreaBuilding a ")
    List<Long> selectAllIds();
}
