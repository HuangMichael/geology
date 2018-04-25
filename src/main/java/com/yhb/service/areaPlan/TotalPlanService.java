package com.yhb.service.areaPlan;

import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.areaPlan.*;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaPlan.*;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.etl.EtlTableService;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Service
public class TotalPlanService extends BaseService {
    @Autowired
    TotalPlanRepository totalPlanRepository;

    @Autowired
    TotalPlanDescRepository totalPlanDescRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    VProvPlanRepository vProvPlanRepository;

    @Autowired
    VCityPlanRepository vCityPlanRepository;

    @Autowired
    EtlTableService etlTableService;

    @Autowired
    VTotalPlanAreaRepository vTotalPlanAreaRepository;

    /**
     * @param session
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
     * @param memo //保存总体规划的备注信息。在地图上显示为该地理要素的区块编号和名称。如： A01绣针河口-柘汪河口
     * @return 保存总体规划，同时保存当前用户的授权码
     */
    public TotalPlan save(HttpSession session, Long id, Long areaId, String planDesc, Long cityId, Long districtId, String beginYear, String endYear,
                          Double buildSize, Double nyBuildSize, Double jsBuildSize, Double stBuildSize, Double reportSize, String status, String memo) {
        if (areaId == null) {
            return null;
        }
        TotalPlan totalPlan = new TotalPlan();
        //如果选择了区块，则有了所在市、区县。没有选择区块，则市、区县也为空
        Area area = areaRepository.findOne(areaId);
        String authKey = DataFilterUtils.getAuthKey(session);
        totalPlan.setId(id);
        totalPlan.setArea(area);
        totalPlan.setCity(area.getCity());
        totalPlan.setDistrict(area.getDistrict());
        totalPlan.setPlanDesc(planDesc);
//        if (cityId == null) {
//            totalPlan.setCity(null);
//        } else {
//            totalPlan.setCity(locationRepository.findOne(cityId));
//        }
//        if (districtId == null) {
//            totalPlan.setDistrict(null);
//        } else {
//            totalPlan.setDistrict(locationRepository.findOne(districtId));
//        }

        totalPlan.setBeginYear(DateUtils.convertStr2DateWithDefault(beginYear, "yyyy-MM-dd", true));
        totalPlan.setEndYear(DateUtils.convertStr2DateWithDefault(endYear, "yyyy-MM-dd", false));
        totalPlan.setBuildSize(buildSize);
        totalPlan.setNyBuildSize(nyBuildSize);
        totalPlan.setJsBuildSize(jsBuildSize);
        totalPlan.setStBuildSize(stBuildSize);
        totalPlan.setReportSize(reportSize);
        totalPlan.setStatus(status);
        totalPlan.setMemo(memo);//保存总体规划的备注信息。在地图上显示为该地理要素的区块编号和名称。如 A01绣针河口-柘汪河口
        totalPlan.setAuthKey(authKey);
        return totalPlanRepository.save(totalPlan);
    }

    /**
     * @param id 根据ID删除总体规划信息
     */
    public ReturnObject delete(Long id) {
        TotalPlan totalPlan = totalPlanRepository.findOne(id);
        if (totalPlan == null) {
            return commonDataService.getReturnType(totalPlan != null, "", "id为" + id + "的总体规划不存在,请确认！");
        }
        try {
            totalPlanRepository.delete(totalPlan);
            //再查询一次查看是否删除
            TotalPlan totalPlan1 = totalPlanRepository.findOne(id);
            return commonDataService.getReturnType(totalPlan1 == null, "总体规划信息删除成功!", "总体规划信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "总体规划信息删除成功！", "总体规划信息删除失败！");
        }
    }

    /**
     * @param id 总体规划id
     * @return 总体规划
     */
    public TotalPlan findById(Long id) {
        return totalPlanRepository.findById(id);
    }

    /**
     * @return 查询所有总体规划
     */
    public List<TotalPlan> findAll() {
        return totalPlanRepository.findAll();
    }

    /**
     * @return 根据areaId查询所有总体规划
     */
    public List<TotalPlan> findByAreaId(Long areaId) {
        return totalPlanRepository.findByAreaId(areaId);
    }

    /**
     * @param areaId 区块id
     * @return 根据区块查询该区块的用地类型统计数据
     */
    public List<Object> findAreaTypeSizeByAreaId(Long areaId) {
        return totalPlanRepository.findAreaTypeSizeByAreaId(areaId);
    }

    /**
     * @param areaId
     * @return 根据区块查询该区块的用地类型统计数据求和
     */
    public Object findAreaTypeSizeTotalByAreaId(Long areaId) {
        return totalPlanRepository.findAreaTypeSizeTotalByAreaId(areaId);
    }

    public List<Long> selectAllIds() {
        return totalPlanRepository.selectAllIds();
    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {

        return totalPlanRepository.findMaxId();
    }

    /**
     * @return 根据位置查询总体规划描述
     */
    public TotalPlanDesc findTotalPlanDescByLocId(Long locId) {

        return totalPlanDescRepository.findByLocation_IdAndStatusOrderByIdDesc(locId, "1");
    }


    /**
     * @return 省级总体规划统计
     */
    public List<VProvPlan> findProvTotalPlanStat() {
        return vProvPlanRepository.findAll();
    }


    /**
     * @return 市县总体规划统计
     */
    public List<VCityPlan> findCityTotalPlanStat(Long parentId) {
        return vCityPlanRepository.findByParentId(parentId);
    }

    /**
     * 对总体规划记录进行批量审核
     * @param totalPlanIds
     * @return
     */
    public List<TotalPlan> authorizeInBatch(String totalPlanIds) {
        if (totalPlanIds.trim().isEmpty()) {
            return null;
        }
        List<TotalPlan> totalPlanList = new ArrayList<TotalPlan>();
        String array[] = totalPlanIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long totalPlanId = Long.parseLong(array[i]);
            TotalPlan totalPlan = totalPlanRepository.findOne(totalPlanId);
            if (totalPlan != null) {
                totalPlan.setStatus("1");
                totalPlan = totalPlanRepository.save(totalPlan);
                if (totalPlan.getStatus().equals("1")) {
                    totalPlanList.add(totalPlan);
                }
            }
        }
        return totalPlanList;
    }


    /**
     * @param serviceTableName
     * @param request
     * @param response
     * @return
     */
    public boolean oneKeyExport(String serviceTableName, HttpServletRequest request, HttpServletResponse response) {
        log.info("oneKeyExport export empty table ------------------" + serviceTableName);
        return etlTableService.createExcelTemplate(serviceTableName, request, response);

    }

    /**
     * @param serviceTableName
     */
    public void oneKeyImport(String serviceTableName) {
        log.info("oneKeyExport------------------" + serviceTableName);
    }


    /**
     * @param areaId 区块id
     * @return 根据区块id查询总体规划信息
     */
    public VTotalPlanArea findTotalPlanByAreaId(Long areaId) {
        return vTotalPlanAreaRepository.findOne(areaId);
    }
}
