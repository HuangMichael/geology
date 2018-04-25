package com.yhb.service.areaPlan;

import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.areaPlan.*;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaPlan.*;
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
 * Created by Administrator on 2017/7/19.
 */
@Service
public class ThirteenFivePlanService extends BaseService {
    @Autowired
    ThirteenFivePlanRepository thirteenFivePlanRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CommonDataService commonDataService;

    @Autowired
    VProv135PlanRepository vProv135PlanRepository;


    @Autowired
    VCity135PlanRepository vCity135PlanRepository;

    @Autowired
    ThirteenFivePlanDescRepository thirteenFivePlanDescRepository;


    @Autowired
    V135PlanAreaRepository v135PlanAreaRepository;

    /**
     * @param id 十三五规划id
     * @return 十三五规划
     */
    public ThirteenFivePlan findById(Long id) {
        return thirteenFivePlanRepository.findOne(id);

    }

    /**
     * @return 查询所有十三五规划
     */
    public List<ThirteenFivePlan> findAll() {
        return thirteenFivePlanRepository.findAll();
    }

    /**
     * @return 根据areaId查询所有十三五规划
     */
    public ThirteenFivePlan findByAreaId(Long areaId) {
        return thirteenFivePlanRepository.findByAreaId(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    public List<Object> findAreaTypeSizeByAreaId(Long areaId) {
        return thirteenFivePlanRepository.findAreaTypeSizeByAreaId(areaId);
    }

    /**
     * @param areaId
     * @return 根据区块查询该区块的用地类型统计数据求和
     */
    public Object findAreaTypeSizeTotalByAreaId(Long areaId) {
        return thirteenFivePlanRepository.findAreaTypeSizeTotalByAreaId(areaId);
    }

    /**
     * @param id
     * @param areaId
     * @param planDesc
     * @param cityId
     * @param districtId
     * @param beginYear
     * @param endYear
     * @param buildSize
     * @param nyBuildSize
     * @param jsBuildSize
     * @param stBuildSize
     * @param reportSize
     * @param status
     * @return 保存十三五规划，同时保存当前用户的授权码
     */
    public ThirteenFivePlan save(HttpSession session, Long id, Long areaId, String planDesc, Long cityId, Long districtId, String beginYear, String endYear,
                                 Double buildSize, Double nyBuildSize, Double jsBuildSize, Double stBuildSize, Double reportSize, String status, String memo) {
        if (areaId == null) {
            return null;
        }
        String authKey = DataFilterUtils.getAuthKey(session);
        ThirteenFivePlan thirteenFivePlan = new ThirteenFivePlan();
        //如果选择了区块，则有了所在市、区县。没有选择区块，则市、区县也为空
        Area area = areaRepository.findOne(areaId);
        thirteenFivePlan.setId(id);
        thirteenFivePlan.setArea(area);
        thirteenFivePlan.setCity(area.getCity());
        thirteenFivePlan.setDistrict(area.getDistrict());
        thirteenFivePlan.setPlanDesc(planDesc);
//        if (cityId == null) {
//            thirteenFivePlan.setCity(null);
//        } else {
//            thirteenFivePlan.setCity(locationRepository.findOne(cityId));
//        }
//        if (districtId == null) {
//            thirteenFivePlan.setDistrict(null);
//        } else {
//            thirteenFivePlan.setDistrict(locationRepository.findOne(districtId));
//        }
        thirteenFivePlan.setBeginYear(DateUtils.convertStr2DateWithDefault(beginYear, "yyyy-MM-dd", true));
        thirteenFivePlan.setEndYear(DateUtils.convertStr2DateWithDefault(endYear, "yyyy-MM-dd", false));
        thirteenFivePlan.setBuildSize(buildSize);
        thirteenFivePlan.setNyBuildSize(nyBuildSize);
        thirteenFivePlan.setJsBuildSize(jsBuildSize);
        thirteenFivePlan.setStBuildSize(stBuildSize);
        thirteenFivePlan.setReportSize(reportSize);
        thirteenFivePlan.setStatus(status);
        thirteenFivePlan.setMemo(memo);//保存十三五规划的备注信息。在地图上显示为该地理要素的区块编号和名称。如 A01绣针河口-柘汪河口
        thirteenFivePlan.setAuthKey(authKey);
        return thirteenFivePlanRepository.save(thirteenFivePlan);
    }

    /**
     * @param id 根据ID删除十三五规划信息
     */
    public ReturnObject delete(Long id) {
        ThirteenFivePlan thirteenFivePlan = thirteenFivePlanRepository.findOne(id);
        if (thirteenFivePlan == null) {
            return commonDataService.getReturnType(thirteenFivePlan != null, "", "id为" + id + "的十三五规划不存在,请确认！");
        }
        try {
            thirteenFivePlanRepository.delete(thirteenFivePlan);
            //再查询一次查看是否删除
            ThirteenFivePlan thirteenFivePlan1 = thirteenFivePlanRepository.findOne(id);
            return commonDataService.getReturnType(thirteenFivePlan1 == null, "十三五规划信息删除成功!", "十三五规划信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "十三五规划信息删除成功！", "十三五规划信息删除失败！");
        }
    }

    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return thirteenFivePlanRepository.findMaxId();
    }

    /**
     * @param thirteenFivePlanIds
     * @return
     */
    public List<ThirteenFivePlan> authorizeInBatch(String thirteenFivePlanIds) {
        if (thirteenFivePlanIds.trim().isEmpty()) {
            return null;
        }
        List<ThirteenFivePlan> thirteenFivePlanList = new ArrayList<ThirteenFivePlan>();
        String array[] = thirteenFivePlanIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long thirteenFivePlanId = Long.parseLong(array[i]);
            ThirteenFivePlan thirteenFivePlan = thirteenFivePlanRepository.findOne(thirteenFivePlanId);
            if (thirteenFivePlan != null) {
                thirteenFivePlan.setStatus("1");
                thirteenFivePlan = thirteenFivePlanRepository.save(thirteenFivePlan);
                if (thirteenFivePlan.getStatus().equals("1")) {
                    thirteenFivePlanList.add(thirteenFivePlan);
                }
            }
        }
        return thirteenFivePlanList;
    }


    /**
     * @return 省级十三五规划统计
     */
    public List<VProv135Plan> findProv135PlanStat() {
        return vProv135PlanRepository.findAll();
    }


    /**
     * @return 市县十三五规划统计
     */
    public List<VCity135Plan> findCity135PlanStat(Long parentId) {
        return vCity135PlanRepository.findByParentId(parentId);
    }


    /**
     * @return 根据位置查询总体规划描述
     */
    public ThirteenFivePlanDesc find135PlanDescByLocId(Long locId) {

        return thirteenFivePlanDescRepository.findByLocation_IdAndStatusOrderByIdDesc(locId, "1");
    }


    /**
     * @param areaId 区块id
     * @return 根据区块id查询总体规划信息
     */
    public V135PlanArea find135PlanByAreaId(Long areaId) {
        return v135PlanAreaRepository.findOne(areaId);
    }
}
