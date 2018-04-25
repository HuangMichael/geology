package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectConsRepository;
import com.yhb.dao.areaProject.AreaProjectRepository;
import com.yhb.domain.areaProject.AreaProjectCons;
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
 * Created by Administrator on 2017/10/20.
 * 系统管理-基础数据管理-项目建设基本信息
 */
@Service
public class AreaProjectConsService extends BaseService {

    @Autowired
    AreaProjectConsRepository areaProjectConsRepository;

    @Autowired
    AreaProjectRepository areaProjectRepository;

    @Autowired
    CommonDataService commonDataService;

//    /**
//     * @param session
//     * @param id
//     * @param projectId
//     * @param beginDate
//     * @param endDate
//     * @param mainPurpose
//     * @param projectLeader
//     * @param leaderContact
//     * @param consUnit
//     * @param monitorUnit
//     * @param acceptUnit
//     * @param status
//     * @return 保存项目项目建设基本信息
//     */
//    public AreaProjectCons save(HttpSession session, Long id, Long projectId, String beginDate, String endDate, String mainPurpose,
//                                String projectLeader, String leaderContact, String consUnit, String monitorUnit, String acceptUnit, String status) {
//        if (projectId == null) {
//            return null;
//        }
//        String authKey = DataFilterUtils.getAuthKey(session);
//        AreaProjectCons areaProjectCons = new AreaProjectCons();
//        areaProjectCons.setId(id);
//        areaProjectCons.setProject(areaProjectRepository.findOne(projectId));
//        areaProjectCons.setBeginDate(DateUtils.convertStr2DateWithDefault(beginDate, "yyyy-MM-dd", true));
//        areaProjectCons.setEndDate(DateUtils.convertStr2DateWithDefault(endDate, "yyyy-MM-dd", false));
//        areaProjectCons.setMainPurpose(mainPurpose);
//        areaProjectCons.setProjectLeader(projectLeader);
//        areaProjectCons.setLeaderContact(leaderContact);
//        areaProjectCons.setConsUnit(consUnit);
//        areaProjectCons.setMonitorUnit(monitorUnit);
//        areaProjectCons.setAcceptUnit(acceptUnit);
//        areaProjectCons.setStatus(status);
//        areaProjectCons.setAuthKey(authKey);
//        return areaProjectConsRepository.save(areaProjectCons);
//    }

    /**
     * @param id
     * @param projectId
     * @param status
     * @return 保存项目建设基本信息
     */
    public AreaProjectCons save(Long id, Long projectId, String status) {
        if (projectId == null) {
            return null;
        }
        AreaProjectCons areaProjectCons = new AreaProjectCons();
        areaProjectCons.setId(id);
        areaProjectCons.setProject(areaProjectRepository.findOne(projectId));
        areaProjectCons.setStatus(status);
        return areaProjectConsRepository.save(areaProjectCons);
    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        areaProjectConsRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除项目建设基本信息
     */
    public ReturnObject delete(Long id) {
        AreaProjectCons areaProjectCons = areaProjectConsRepository.findOne(id);
        if (areaProjectCons == null) {
            return commonDataService.getReturnType(areaProjectCons != null, "", "id为" + id + "的项目建设基本信息不存在,请确认！");
        }
        try {
            areaProjectConsRepository.delete(areaProjectCons);
            //再查询一次查看是否删除
            AreaProjectCons areaProjectCons1 = areaProjectConsRepository.findOne(id);
            return commonDataService.getReturnType(areaProjectCons1 == null, "项目建设基本信息删除成功!", "项目建设基本信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "项目建设基本信息删除成功！", "项目建设基本信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public AreaProjectCons findById(Long id) {
        return areaProjectConsRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目项目建设基本信息
     */

    public List<AreaProjectCons> findAll() {
        return areaProjectConsRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectConsRepository.selectAllIds();
    }

    /**
     * @param projectConsIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<AreaProjectCons> authorizeInBatch(String projectConsIds) {
        if (projectConsIds.trim().isEmpty()) {
            return null;
        }
        List<AreaProjectCons> areaProjectConsList = new ArrayList<AreaProjectCons>();
        String array[] = projectConsIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            AreaProjectCons areaProjectCons = areaProjectConsRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (areaProjectCons != null && areaProjectCons.getStatus().equals("0")) {
                areaProjectCons.setStatus("1");
                areaProjectCons = areaProjectConsRepository.save(areaProjectCons);
                if (areaProjectCons.getStatus().equals("1")) {
                    areaProjectConsList.add(areaProjectCons);
                }
            }
        }
        return areaProjectConsList;
    }
}
