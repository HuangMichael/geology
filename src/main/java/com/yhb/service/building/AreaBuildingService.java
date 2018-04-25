package com.yhb.service.building;

import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.building.AreaBuildingRepository;
import com.yhb.dao.building.VAreaBuiltStatRepository;
import com.yhb.dao.building.VProvBuildingRepository;
import com.yhb.dao.building.VcityBuildingRepository;
import com.yhb.dao.location.LocationBuildingRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaBuilding.AreaBuilding;
import com.yhb.domain.areaBuilding.VAreaBuiltStat;
import com.yhb.domain.areaBuilding.VProvBuilding;
import com.yhb.domain.areaBuilding.VcityBuilding;
import com.yhb.domain.location.LocationBuilding;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin 2017/5/7.
 * 围垦进度信息业务类
 */
@Service
public class AreaBuildingService extends BaseService {


    @Autowired
    AreaBuildingRepository areaBuildingRepository;

    @Autowired
    LocationBuildingRepository locationBuildingRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    VProvBuildingRepository vProvBuildingRepository;

    @Autowired
    VcityBuildingRepository vcityBuildingRepository;

    @Autowired
    LocationRepository locationRepository;


    @Autowired
    VAreaBuiltStatRepository vAreaBuiltStatRepository;

    /**
     * @param session
     * @param id
     * @param areaId
     * @param buildDesc
     * @param beginYear
     * @param endYear
     * @param cityId
     * @param districtId
     * @param buildSize
     * @param nyBuildSize
     * @param jsBuildSize
     * @param stBuildSize
     * @param manager
     * @param status
     * @return 保存围垦进度信息信息
     */
    public AreaBuilding save(HttpSession session, Long id, Long areaId, String buildDesc, String beginYear,
                             String endYear, Long cityId, Long districtId, Double buildSize, Double nyBuildSize,
                             Double jsBuildSize, Double stBuildSize, String manager, String status) {
        if (areaId == null) {
            return null;
        }

        Area area = areaRepository.findOne(areaId);
        String authKey = DataFilterUtils.getAuthKey(session);
        AreaBuilding areaBuilding = new AreaBuilding();
        areaBuilding.setId(id);
        areaBuilding.setArea(area);
        areaBuilding.setBuildDesc(buildDesc);
        areaBuilding.setBeginYear(DateUtils.convertStr2DateWithDefault(beginYear, "yyyy-MM-dd", true));
        areaBuilding.setEndYear(DateUtils.convertStr2DateWithDefault(endYear, "yyyy-MM-dd", false));
        if (cityId == null) {
            areaBuilding.setCity(area.getCity());
        } else {
            areaBuilding.setCity(area.getCity());
        }
        if (districtId == null) {
            areaBuilding.setDistrict(area.getDistrict());
        } else {
            areaBuilding.setDistrict(area.getDistrict());
        }
        areaBuilding.setBuildSize(buildSize);
        areaBuilding.setNyBuildSize(nyBuildSize);
        areaBuilding.setJsBuildSize(jsBuildSize);
        areaBuilding.setStBuildSize(stBuildSize);
        areaBuilding.setManager(manager);
        areaBuilding.setStatus(status);
        areaBuilding.setAuthKey(authKey);
        return areaBuildingRepository.save(areaBuilding);
    }

    /**
     * @param id 围垦进度信息id
     * @return 围垦进度信息
     */
    public AreaBuilding findById(Long id) {
        return areaBuildingRepository.findOne(id);
    }


    /**
     * @return 查询所有围垦进度信息
     */
    public List<AreaBuilding> findAll() {
        return areaBuildingRepository.findAll();
    }

    /**
     * @return 根据areaId查询所有围垦进度信息
     */
    public List<AreaBuilding> findByAreaId(Long areaId) {
        return areaBuildingRepository.findByAreaId(areaId);
    }

    /**
     * @param id 根据ID删除 围垦进度信息
     */
    public ReturnObject delete(Long id) {
        AreaBuilding areaBuilding = areaBuildingRepository.findOne(id);
        if (areaBuilding == null) {
            return commonDataService.getReturnType(areaBuilding != null, "", "id为" + id + "的围垦进度信息不存在,请确认！");
        }
        try {
            areaBuildingRepository.delete(areaBuilding);
            //再查询一次查看是否删除
            AreaBuilding areaBuilding1 = areaBuildingRepository.findOne(id);
            return commonDataService.getReturnType(areaBuilding1 == null, "围垦进度信息删除成功!", "围垦进度信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "围垦进度信息删除成功！", "围垦进度信息删除失败！");
        }
    }


    /**
     * @param locationId 位置id
     * @return 根据位置查询该位置的围垦进度描述信息
     */
    public List<LocationBuilding> findDescByLocationId(Long locationId) {
        return locationBuildingRepository.findByLocation_Id(locationId);
    }

    /**
     * @param locationId 位置id
     * @return 根据位置查询区块信息
     */
    public List<Area> findAreasByLocationId(Long locationId) {
        return areaRepository.findAreasByLocationId(locationId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块id查询该区块的现状描述信息
     */
    public List<Object> findAreaDescById(Long areaId) {
        return areaBuildingRepository.findAreaDescById(areaId);
    }
//
//    /**
//     * @param areaId 区块id
//     * @return 根据区块查询该区块的图片、视频列表信息
//     */
//    public List<Object> findAreaImgsById(Long areaId) {
//        return vAreaBuildingRepository.findAreaImgsById(areaId);
//    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    public List<Object> findAreaTypeSizeByAreaId(Long areaId) {
        return areaBuildingRepository.findAreaTypeSizeByAreaId(areaId);
    }

    /**
     * @param areaId
     * @return 根据区块查询该区块的用地类型统计数据求和
     */
    public Object findAreaTypeSizeTotalByAreaId(Long areaId) {
        return areaBuildingRepository.findAreaTypeSizeTotalByAreaId(areaId);
    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {

        return areaBuildingRepository.findMaxId();
    }

//
//    /**
//     * @return 根据位置查询总体规划描述
//     */
//    public AreaBuildingDesc findAreaBuildingDescByLocId(Long locId) {
//
//        return areaBuildingDescRepository.findByLocation_IdAndStatusOrderByIdDesc(locId, "1");
//    }
//
//
//    /**
//     * @return 省级总体规划统计
//     */
//    public List<VAreaProjectProStat> findProvAreaBuildingStat() {
//        return vProvPlanRepository.findAll();
//    }
//
//
//    /**
//     * @return 市县总体规划统计
//     */
//    public List<VAreaProjectCityStat> findCityAreaBuildingStat(Long parentId) {
//        return vCityPlanRepository.findByParentId(parentId);
//    }


    /**
     * @return 省级已围统计
     */
    public List<VProvBuilding> findProvBuildingStat() {
        return vProvBuildingRepository.findAll();
    }


    /**
     * @return 市县已围统计
     */
    public List<VcityBuilding> findCityBuildingStat(Long parentId) {
        return vcityBuildingRepository.findByParentId(parentId);
    }


    /**
     * @param areaId 区块id
     * @return 根据区块id查询统计已围面积
     */
    public VAreaBuiltStat findStatByAreaId(Long areaId) {
        return vAreaBuiltStatRepository.findByArea_Id(areaId);
    }

    /**
     * @param areaBuildingIds
     * @return
     */
    public List<AreaBuilding> authorizeInBatch(String areaBuildingIds) {
        if (areaBuildingIds.trim().isEmpty()) {
            return null;
        }
        List<AreaBuilding> areaBuildingList = new ArrayList<AreaBuilding>();
        String array[] = areaBuildingIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long areaBuildingId = Long.parseLong(array[i]);
            AreaBuilding areaBuilding = areaBuildingRepository.findOne(areaBuildingId);
            if (areaBuilding != null) {
                areaBuilding.setStatus("1");
                areaBuilding = areaBuildingRepository.save(areaBuilding);
                if (areaBuilding.getStatus().equals("1")) {
                    areaBuildingList.add(areaBuilding);
                }
            }
        }
        return areaBuildingList;
    }
}
