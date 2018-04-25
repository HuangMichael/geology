package com.yhb.service.building;

import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.building.AreaBuildingRepository;
import com.yhb.dao.building.VAreaBuildingRepository;
import com.yhb.dao.building.VAreaStatisticsRepository;
import com.yhb.dao.building.VcityBuildingRepository;
import com.yhb.dao.location.LocationBuildingRepository;
import com.yhb.domain.areaBuilding.AreaBuilding;
import com.yhb.domain.areaBuilding.VAreaBuilding;
import com.yhb.domain.areaBuilding.VAreaStatistics;
import com.yhb.domain.areaBuilding.VcityBuilding;
import com.yhb.domain.location.Location;
import com.yhb.domain.location.LocationBuilding;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin 2017/5/7.
 * 围垦信息业务类
 */
@Service
public class VAreaBuildingService extends BaseService {


    @Autowired
    AreaBuildingRepository areaBuildingRepository;

    @Autowired
    VAreaBuildingRepository vAreaBuildingRepository;


    @Autowired
    LocationBuildingRepository locationBuildingRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    VcityBuildingRepository vcityBuildingRepository;

    @Autowired
    VAreaStatisticsRepository vAreaStatisticsRepository;

    /**
     * @param areaBuilding 围垦信息
     * @return 保存围垦信息信息
     */
    public AreaBuilding save(AreaBuilding areaBuilding) {
        return areaBuildingRepository.save(areaBuilding);

    }

    /**
     * @param id 围垦信息id
     * @return 围垦信息
     */
    public VAreaBuilding findById(Long id) {
        return vAreaBuildingRepository.findOne(id);

    }


    /**
     * @return 查询所有围垦信息
     */
    public List<VAreaBuilding> findAll() {
        return vAreaBuildingRepository.findAll();
    }

    /**
     * @return 查询符合条件的已围垦的用地类型统计数据求和，并按照区块编号分组
     */
    public List<Object> findAreaTypeSizeGroupByAreaNo(List<String> areaNoList) {
        return vAreaBuildingRepository.findAreaTypeSizeGroupByAreaNo(areaNoList);
    }

    /**
     * @return 查询符合条件的已围垦的用地类型统计数据求和，并按照区块编号分组，专为区块统计分析服务
     */
    public List<VAreaStatistics> findAreaTypeSizeGroupByAreaNoForAreaStat(List<String> areaNoList) {
        return vAreaStatisticsRepository.findByAreaNoIn(areaNoList);
    }

    /**
     * @return 查询所有围垦信息
     */
    public List<VcityBuilding> sumByCity() {
        return vcityBuildingRepository.findAll();
    }


    /**
     * @return 根据位置查询所有围垦信息
     */
    public LocationBuilding getLocationBuilding(Long id) {
        return locationBuildingRepository.findByLocation_Id(id).get(0);
    }

    /**
     * @param id
     * @return 根据id查询该区块的各类型面积求和的值
     */
    public VAreaStatistics findAreaTypeSizeTotalById(Long id) {
        return vAreaStatisticsRepository.findById(id);
    }

    public List<Long> selectAllIds(){
        return vAreaBuildingRepository.selectAllIds();
    }
}
