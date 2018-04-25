package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectAcceptRepository;
import com.yhb.dao.areaProject.AreaProjectRepository;
import com.yhb.dao.engineeringType.EngineeringTypeRepository;
import com.yhb.dao.landUsingType.LandUsingTypeRepository;
import com.yhb.domain.areaProject.AreaProjectAccept;
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
 * Created by huangbin on 2017/5/22 0004.
 * 项目验收业务类
 */
@Service
public class AreaProjectAcceptService extends BaseService {

    @Autowired
    AreaProjectAcceptRepository areaProjectAcceptRepository;

    @Autowired
    AreaProjectRepository areaProjectRepository;

    @Autowired
    LandUsingTypeRepository landUsingTypeRepository;

    @Autowired
    EngineeringTypeRepository engineeringTypeRepository;

    @Autowired
    CommonDataService commonDataService;

//    /**
//     * @param session
//     * @param id
//     * @param projectId
//     * @param beginDate
//     * @param endDate
//     * @param mainPurpose
//     * @param landUsingTypeId
//     * @param engineeringTypeId
//     * @param budget
//     * @param investedSum
//     * @param consUnit
//     * @param monitorUnit
//     * @param acceptUnit
//     * @param projectLeader
//     * @param leaderContact
//     * @param taskProgress
//     * @param expert
//     * @param conclusion
//     * @param status
//     * @return 保存项目验收信息
//     */
//    public AreaProjectAccept save(HttpSession session, Long id, Long projectId, String beginDate, String endDate, String mainPurpose, Long landUsingTypeId, Long engineeringTypeId,
//                                  Double budget, Double investedSum, String consUnit, String monitorUnit, String acceptUnit, String projectLeader, String leaderContact,
//                                  String taskProgress, String expert, String conclusion, String status) {
//        if (projectId == null) {
//            return null;
//        }
//        String authKey = DataFilterUtils.getAuthKey(session);
//        AreaProjectAccept areaProjectAccept = new AreaProjectAccept();
//        areaProjectAccept.setId(id);
//        areaProjectAccept.setProject(areaProjectRepository.findOne(projectId));
//        areaProjectAccept.setBeginDate(DateUtils.convertStr2DateWithDefault(beginDate, "yyyy-MM-dd", true));
//        areaProjectAccept.setEndDate(DateUtils.convertStr2DateWithDefault(endDate, "yyyy-MM-dd", false));
//        areaProjectAccept.setMainPurpose(mainPurpose);
//        if (landUsingTypeId == null) {
//            areaProjectAccept.setLandUsingType(null);
//        } else {
//            areaProjectAccept.setLandUsingType(landUsingTypeRepository.findOne(landUsingTypeId));
//        }
//        if (engineeringTypeId == null) {
//            areaProjectAccept.setEngineeringType(null);
//        } else {
//            areaProjectAccept.setEngineeringType(engineeringTypeRepository.findOne(engineeringTypeId));
//        }
//        areaProjectAccept.setBudget(budget);
//        areaProjectAccept.setInvestedSum(investedSum);
//        areaProjectAccept.setConsUnit(consUnit);
//        areaProjectAccept.setMonitorUnit(monitorUnit);
//        areaProjectAccept.setAcceptUnit(acceptUnit);
//        areaProjectAccept.setProjectLeader(projectLeader);
//        areaProjectAccept.setLeaderContact(leaderContact);
//        areaProjectAccept.setTaskProgress(taskProgress);
//        areaProjectAccept.setExpert(expert);
//        areaProjectAccept.setConclusion(conclusion);
//        areaProjectAccept.setStatus(status);
//        areaProjectAccept.setAuthKey(authKey);
//        return areaProjectAcceptRepository.save(areaProjectAccept);
//    }

    /**
     * @param id
     * @param projectId
     * @param expert
     * @param conclusion
     * @param status
     * @return 保存项目验收信息
     */
    public AreaProjectAccept save(Long id, Long projectId, String expert, String conclusion, String status) {
        if (projectId == null) {
            return null;
        }
        AreaProjectAccept areaProjectAccept = new AreaProjectAccept();
        areaProjectAccept.setId(id);
        areaProjectAccept.setProject(areaProjectRepository.findOne(projectId));
        areaProjectAccept.setExpert(expert);
        areaProjectAccept.setConclusion(conclusion);
        areaProjectAccept.setStatus(status);
        return areaProjectAcceptRepository.save(areaProjectAccept);
    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        areaProjectAcceptRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除项目验收信息
     */
    public ReturnObject delete(Long id) {
        AreaProjectAccept areaProjectAccept = areaProjectAcceptRepository.findOne(id);
        if (areaProjectAccept == null) {
            return commonDataService.getReturnType(areaProjectAccept != null, "", "id为" + id + "的项目验收基本信息不存在,请确认！");
        }
        try {
            areaProjectAcceptRepository.delete(areaProjectAccept);
            //再查询一次查看是否删除
            AreaProjectAccept areaProjectAccept1 = areaProjectAcceptRepository.findOne(id);
            return commonDataService.getReturnType(areaProjectAccept1 == null, "项目验收基本信息删除成功!", "项目验收基本信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "项目验收基本信息删除成功！", "项目验收基本信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询项目验收基本信息
     */

    public AreaProjectAccept findById(Long id) {
        return areaProjectAcceptRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目验收信息
     */

    public List<AreaProjectAccept> findAll() {
        return areaProjectAcceptRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectAcceptRepository.selectAllIds();
    }

    /**
     * @param projectAcceptIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<AreaProjectAccept> authorizeInBatch(String projectAcceptIds) {
        if (projectAcceptIds.trim().isEmpty()) {
            return null;
        }
        List<AreaProjectAccept> areaProjectAcceptList = new ArrayList<AreaProjectAccept>();
        String array[] = projectAcceptIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            AreaProjectAccept areaProjectAccept = areaProjectAcceptRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectAcceptList里面，已审核的就不再审核
            if (areaProjectAccept != null && areaProjectAccept.getStatus().equals("0")) {
                areaProjectAccept.setStatus("1");
                areaProjectAccept = areaProjectAcceptRepository.save(areaProjectAccept);
                if (areaProjectAccept.getStatus().equals("1")) {
                    areaProjectAcceptList.add(areaProjectAccept);
                }
            }
        }
        return areaProjectAcceptList;
    }
}
