package com.yhb.dao.area;

import com.yhb.domain.area.VArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 围垦区块视图信息数据接口
 */
public interface VAreaRepository extends JpaRepository<VArea, Long> {

    /**
     * @return 查询所有的id
     */
    @Query("select a.id from VArea a ")
    List<Long> selectAllIds();


    /**
     * @param authKey      数据过滤关键字
     * @param cityName
     * @param districtName
     * @param areaNo       区块编号
     * @param areaName     区块名称
     * @return
     */
    List<VArea> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains(String authKey, String cityName, String districtName, String areaNo, String areaName, String status);

    /**
     * @param authKey      数据过滤关键字
     * @param cityName
     * @param districtName
     * @param areaNo       区块编号
     * @param areaName     区块名称
     * @return
     */
    List<VArea> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContainsAndIdInOrderById(String authKey, String cityName, String districtName, String areaNo, String areaName, String status, List<Long> selectedIds);

    /**
     * @param authKey      数据过滤关键字
     * @param cityName
     * @param districtName
     * @param areaNo       区块编号
     * @param areaName     区块名称
     * @param pageable     可分页参数
     * @return
     */
    Page<VArea> findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains(String authKey, String cityName, String districtName, String areaNo, String areaName, String status, Pageable pageable);


}
