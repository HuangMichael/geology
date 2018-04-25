package com.yhb.service.areaProject;

import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.areaProject.*;
import com.yhb.dao.areaProject.projectStat.VProjectPlanStatRepository;
import com.yhb.dao.areaProject.projectStat.VProjectStatRepository;
import com.yhb.dao.engineeringType.EngineeringTypeRepository;
import com.yhb.dao.landUsingType.LandUsingTypeRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.areaProject.*;
import com.yhb.domain.areaProject.projectStat.*;
import com.yhb.domain.location.Location;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.project.Project4Sel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 项目信息业务类
 */
@Service
public class AreaProjectService extends BaseService {
    @Autowired
    AreaRepository areaRepository;

    @Autowired
    AreaProjectRepository areaProjectRepository;

    @Autowired
    LandUsingTypeRepository landUsingTypeRepository;

    @Autowired
    EngineeringTypeRepository engineeringTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    @Autowired
    VProjectStatRepository vProjectStatRepository;

    @Autowired
    VProjectPlanStatRepository vProjectPlanStatRepository;

    @Autowired
    LocationRepository locationRepository;


    /**
     * @param session
     * @param id
     * @param projectNo
     * @param projectName
     * @param areaId
     * @param cityId
     * @param districtId
     * @param beginYear
     * @param endYear
     * @param projectSize
     * @param mainPurpose
     * @param projectLeader
     * @param leaderContact
     * @param consUnit
     * @param monitorUnit
     * @param acceptUnit
     * @param budget
     * @param investedSum
     * @param taskProgress
     * @param landUsingTypeId
     * @param engineeringTypeId
     * @param status
     * @return 保存项目信息，同时保存当前用户的授权码
     */
    public AreaProject save(HttpSession session, Long id, String projectNo, String projectName, Long areaId, Long cityId, Long districtId,
                            String beginYear, String endYear, Double projectSize, String mainPurpose, String projectLeader, String leaderContact, String consUnit, String monitorUnit,
                            String acceptUnit, Double budget, Double investedSum, String taskProgress, Long landUsingTypeId, Long engineeringTypeId, String status, String buildStatus, String important) {
        String authKey = DataFilterUtils.getAuthKey(session);
        AreaProject areaProject = new AreaProject();
        areaProject.setId(id);
        areaProject.setProjectNo(projectNo);
        areaProject.setProjectName(projectName);
        //如果选择了区块，则有了所在市、区县。没有选择区块，则市、区县也为空
        if (areaId == null) {
            areaProject.setArea(null);
            areaProject.setCity(null);
            areaProject.setDistrict(null);
        } else {
            Area area = areaRepository.findOne(areaId);
            areaProject.setArea(area);
            if (area != null) {
                areaProject.setCity(area.getCity());
                areaProject.setDistrict(area.getDistrict());
            } else {
                areaProject.setCity(null);
                areaProject.setDistrict(null);
            }
        }
        areaProject.setBeginYear(DateUtils.convertStr2DateWithDefault(beginYear, "yyyy-MM-dd", true));
        areaProject.setEndYear(DateUtils.convertStr2DateWithDefault(endYear, "yyyy-MM-dd", false));
        areaProject.setProjectSize(projectSize);
        areaProject.setMainPurpose(mainPurpose);
        areaProject.setProjectLeader(projectLeader);
        areaProject.setLeaderContact(leaderContact);
        areaProject.setConsUnit(consUnit);
        areaProject.setMonitorUnit(monitorUnit);
        areaProject.setAcceptUnit(acceptUnit);
        areaProject.setBudget(budget);
        areaProject.setInvestedSum(investedSum);
        areaProject.setTaskProgress(taskProgress);
        if (landUsingTypeId == null) {
            areaProject.setLandUsingType(null);
        } else {
            areaProject.setLandUsingType(landUsingTypeRepository.findOne(landUsingTypeId));
        }
        if (engineeringTypeId == null) {
            areaProject.setEngineeringType(null);
        } else {
            areaProject.setEngineeringType(engineeringTypeRepository.findOne(engineeringTypeId));
        }
        areaProject.setStatus(status);
        areaProject.setBuildStatus(buildStatus);
        areaProject.setImportant(important);//设置是否为重大项目
        areaProject.setAuthKey(authKey);
        return areaProjectRepository.save(areaProject);
    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        areaProjectRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除项目信息
     */
    public ReturnObject delete(Long id) {
        AreaProject areaProject = areaProjectRepository.findOne(id);
        if (areaProject == null) {
            return commonDataService.getReturnType(areaProject != null, "", "id为" + id + "的项目信息不存在,请确认！");
        }
        try {
            areaProjectRepository.delete(areaProject);
            //再查询一次查看是否删除
            AreaProject areaProject1 = areaProjectRepository.findOne(id);
            return commonDataService.getReturnType(areaProject1 == null, "项目信息删除成功！", "项目信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "项目信息删除成功！", "项目信息删除失败！");
        }
    }

    /**
     * @param id 项目id
     * @return 根据id查询区块信息
     */

    public AreaProject findById(Long id) {
        return areaProjectRepository.findOne(id);
    }

    /**
     * @return 查询所有的项目信息
     */

    public List<AreaProject> findAll() {
        return areaProjectRepository.findAllOrderByImportantDesc();
    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectRepository.selectAllIds();
    }

    /**
     * @return 获取所有项目的 预算、已投资额 信息
     */
    public List<Object> getAllProjectFund() {
        return areaProjectRepository.getAllProjectFund();
    }

    /**
     * @param idList
     * @return 获取符合查询条件的项目的 预算、已投资额 信息
     */
    public List<Object> getProjectFundByIds(List<Long> idList) {
        return areaProjectRepository.getProjectFundByIds(idList);
    }

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    public List<Long> selectOtherIds(Long id) {
        return areaProjectRepository.selectOtherIds(id);
    }

    /**
     * @param projectNo
     * @return
     */
    public AreaProject findByProjectNo(String projectNo) {
        return areaProjectRepository.findByProjectNo(projectNo);
    }

    /**
     * @param id
     * @param projectNo
     * @return 编辑项目信息时，根据前端传过来的用户输入的新项目编号和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    public AreaProject findProjectDuplicateByNoAndId(Long id, String projectNo) {
        //查询不是该id的其他项目id列表
        List<Long> otherIds = selectOtherIds(id);
        return areaProjectRepository.findByProjectNoAndIdIn(projectNo, otherIds);
    }

    /**
     * @param projectName
     * @return
     */
    public AreaProject findByProjectName(String projectName) {
        return areaProjectRepository.findByProjectName(projectName);
    }

    /**
     * @param id
     * @param projectName
     * @return 编辑项目信息时，根据前端传过来的用户输入的新项目名称和id查询是否与其他的重复。如果重复，返回一个AreaProject；不重复返回null
     */
    public AreaProject findProjectDuplicateByNameAndId(Long id, String projectName) {
        //查询不是该id的其他项目id列表
        List<Long> otherIds = selectOtherIds(id);
        return areaProjectRepository.findByProjectNameAndIdIn(projectName, otherIds);
    }

    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return areaProjectRepository.findMaxId();
    }


    /**
     * @param locLevel
     * @param locId
     * @return 查询全省在建和已建项目信息，按照工程类型统计
     */
    public Map<String, Integer> findProjectProEneStat(Long locLevel, Long locId) {
        List<VAreaProjectStat> areaProjectStatList = new ArrayList<>();
        Location location = locationRepository.findOne(locId);
        if (locLevel == 0l) {
            areaProjectStatList = vProjectStatRepository.findByProv(location.getLocName());
        } else if (locLevel == 1l) {
            areaProjectStatList = vProjectStatRepository.findByCity(location.getLocName());
        }
        Map<String, Integer> map = areaProjectStatList.stream().collect(
                Collectors.groupingBy(VAreaProjectStat::getEngineeringType,
                        Collectors.reducing(0, VAreaProjectStat::getProjectNum, Integer::sum)));
        log.info(map.toString());
        return map;
    }


    /**
     * @param locLevel
     * @param locId
     * @return 查询全省在建和已建项目信息，按照行政区划统计
     */
    public Map<String, Integer> findProjectProStat(Long locLevel, Long locId) {
        List<VAreaProjectStat> areaProjectStatList;
        Location location = locationRepository.findOne(locId);
        Map<String, Integer> map = new HashMap<>();
        if (locLevel == 0l) {
            areaProjectStatList = vProjectStatRepository.findByProv(location.getLocName());
            map = areaProjectStatList.stream().collect(
                    Collectors.groupingBy(VAreaProjectStat::getCity,
                            Collectors.reducing(0, VAreaProjectStat::getProjectNum, Integer::sum)));
        } else if (locLevel == 1l) {
            areaProjectStatList = vProjectStatRepository.findByCity(location.getLocName());
            map = areaProjectStatList.stream().collect(
                    Collectors.groupingBy(areaProjectStat -> areaProjectStat.getDistrict(),
                            Collectors.reducing(0, VAreaProjectStat::getProjectNum, Integer::sum)));
        }
        log.info(map.toString());
        return map;
    }


    /**
     * @param locLevel
     * @param locId
     * @return 查询规划项目信息统计 按照工程类型
     */
    public Map<String, Integer> findProjectPlanEneStat(Long locLevel, Long locId) {
        List<VAreaProjectPlanStat> areaProjectStatList = new ArrayList<>();
        Location location = locationRepository.findOne(locId);
        if (locLevel == 0l) {
            areaProjectStatList = vProjectPlanStatRepository.findByProv(location.getLocName());
        } else if (locLevel == 1l) {
            areaProjectStatList = vProjectPlanStatRepository.findByCity(location.getLocName());
        }
        Map<String, Integer> map = areaProjectStatList.stream().collect(
                Collectors.groupingBy(VAreaProjectPlanStat::getEngineeringType,
                        Collectors.reducing(0, VAreaProjectPlanStat::getProjectNum, Integer::sum)));
        log.info(map.toString());
        return map;
    }


    /**
     * @param locLevel
     * @param locId
     * @return 查询规划项目信息统计  按照行政区划
     */
    public Map<String, Integer> findProjectPlanStat(Long locLevel, Long locId) {
        List<VAreaProjectPlanStat> areaProjectStatList;
        Location location = locationRepository.findOne(locId);
        Map<String, Integer> map = new HashMap<>();
        if (locLevel == 0l) {
            areaProjectStatList = vProjectPlanStatRepository.findByProv(location.getLocName());
            map = areaProjectStatList.stream().collect(
                    Collectors.groupingBy(VAreaProjectPlanStat::getCity,
                            Collectors.reducing(0, VAreaProjectPlanStat::getProjectNum, Integer::sum)));
        } else if (locLevel == 1l) {
            areaProjectStatList = vProjectPlanStatRepository.findByCity(location.getLocName());
            map = areaProjectStatList.stream().collect(
                    Collectors.groupingBy(VAreaProjectPlanStat::getDistrict,
                            Collectors.reducing(0, VAreaProjectPlanStat::getProjectNum, Integer::sum)));
        }
        log.info(map.toString());
        return map;
    }


    /**
     * @param projectIds
     * @return
     */
    public List<AreaProject> authorizeInBatch(String projectIds) {
        if (projectIds.trim().isEmpty()) {
            return null;
        }
        List<AreaProject> projectList = new ArrayList<>();
        String array[] = projectIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long areaProjectId = Long.parseLong(array[i]);
            AreaProject areaProject = areaProjectRepository.findOne(areaProjectId);
            if (areaProject != null && areaProject.getStatus().equals("0")) {
                areaProject.setStatus("1");
                areaProject = areaProjectRepository.save(areaProject);
                if (areaProject.getStatus().equals("1")) {
                    projectList.add(areaProject);
                }
            }
        }
        return projectList;
    }


    /**
     * @param buildStatus 项目建设状态
     * @return 根据项目建设状态查询项目信息
     */
    public List<AreaProject> findByBuildStatus(String buildStatus) {
        return areaProjectRepository.findByBuildStatus(buildStatus);
    }

    /**
     * @param session
     * @return 根据授权码查询项目信息
     */
    public List<AreaProject> findMyProjects(HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return areaProjectRepository.findByAuthKeyStartingWith(authKey);
    }

    /**
     * @return 查询可供下拉选择的在建和已建项目信息
     * 建设状态为1或者2的
     */
    public List<Project4Sel> findAll4Sel() {
        List<Project4Sel> project4SelList = new ArrayList<>();
        for (AreaProject project : areaProjectRepository.findByBuildStatusNotOrderByImportantDesc("0")) {
            project4SelList.add(new Project4Sel(project.getId(), project.getProjectNo(), project.getProjectName()));
        }
        return project4SelList;
    }


    /**
     * @return 根据位置id查询所有在建或者已建项目
     * 建设状态为1或者2的
     */
    public List<Project4Sel> findAll4SelByLocId(Long locId) {
        List<Project4Sel> project4SelList = new ArrayList<>();
        for (AreaProject project : areaProjectRepository.findByBuildStatusNotAndLocIdOrderByImportantDesc("0",locId)) {
            project4SelList.add(new Project4Sel(project.getId(), project.getProjectNo(), project.getProjectName()));
        }
        return project4SelList;
    }

    /**
     * @return 查询可供下拉选择的规划项目信息
     * 建设状态为0的
     */
    public List<Project4Sel> findPlanProjectAll4Sel() {
        List<Project4Sel> project4SelList = new ArrayList<>();
        for (AreaProject project : areaProjectRepository.findByBuildStatusOrderByImportantDesc("0")) {
            project4SelList.add(new Project4Sel(project.getId(), project.getProjectNo(), project.getProjectName()));
        }
        return project4SelList;
    }

    /**
     * @param locId
     * @return 根据位置id查询所有的规划项目
     */
    public List<Project4Sel> findPlanProjectAll4SelByLocId(Long locId) {
        List<Project4Sel> project4SelList = new ArrayList<>();
        for (AreaProject project : areaProjectRepository.findByBuildStatusAndLocIdOrderByImportantDesc("0",locId)) {
            project4SelList.add(new Project4Sel(project.getId(), project.getProjectNo(), project.getProjectName()));
        }
        return project4SelList;
    }
}
