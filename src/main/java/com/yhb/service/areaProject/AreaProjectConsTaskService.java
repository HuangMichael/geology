package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.AreaProjectConsTaskRepository;
import com.yhb.dao.areaProject.AreaProjectRepository;
import com.yhb.dao.engineeringType.EngineeringTypeRepository;
import com.yhb.domain.areaProject.AreaProjectConsTask;
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
 */
@Service
public class AreaProjectConsTaskService extends BaseService {

    @Autowired
    AreaProjectConsTaskRepository areaProjectConsTaskRepository;

    @Autowired
    AreaProjectRepository areaProjectRepository;

    @Autowired
    EngineeringTypeRepository engineeringTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param consTask
     * @param status
     * @return 保存项目项目建设任务信息
     */
    public AreaProjectConsTask save(Long id, Long projectId, String beginDate, String endDate, String consTask, String status) {
        if (projectId == null) {
            return null;
        }
        AreaProjectConsTask areaProjectConsTask = new AreaProjectConsTask();
        areaProjectConsTask.setId(id);
        areaProjectConsTask.setProject(areaProjectRepository.findOne(projectId));
        areaProjectConsTask.setBeginDate(DateUtils.convertStr2DateWithDefault(beginDate, "yyyy-MM-dd", true));
        areaProjectConsTask.setEndDate(DateUtils.convertStr2DateWithDefault(endDate, "yyyy-MM-dd", false));
        areaProjectConsTask.setConsTask(consTask);
        areaProjectConsTask.setStatus(status);
        return areaProjectConsTaskRepository.save(areaProjectConsTask);
    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        areaProjectConsTaskRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除项目建设任务信息
     */
    public ReturnObject delete(Long id) {
        AreaProjectConsTask areaProjectConsTask = areaProjectConsTaskRepository.findOne(id);
        if (areaProjectConsTask == null) {
            return commonDataService.getReturnType(areaProjectConsTask != null, "", "id为" + id + "的项目建设任务信息不存在,请确认！");
        }
        try {
            areaProjectConsTaskRepository.delete(areaProjectConsTask);
            //再查询一次查看是否删除
            AreaProjectConsTask areaProjectConsTask1 = areaProjectConsTaskRepository.findOne(id);
            return commonDataService.getReturnType(areaProjectConsTask1 == null, "项目建设任务信息删除成功!", "项目建设任务信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "项目建设任务信息删除成功！", "项目建设任务信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public AreaProjectConsTask findById(Long id) {
        return areaProjectConsTaskRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目项目建设任务信息
     */

    public List<AreaProjectConsTask> findAll() {
        return areaProjectConsTaskRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectConsTaskRepository.selectAllIds();
    }

    /**
     * @param projectConsTaskIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<AreaProjectConsTask> authorizeInBatch(String projectConsTaskIds) {
        if (projectConsTaskIds.trim().isEmpty()) {
            return null;
        }
        List<AreaProjectConsTask> projectConsTaskList = new ArrayList<AreaProjectConsTask>();
        String array[] = projectConsTaskIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            AreaProjectConsTask areaProjectConsTask = areaProjectConsTaskRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (areaProjectConsTask != null && areaProjectConsTask.getStatus().equals("0")) {
                areaProjectConsTask.setStatus("1");
                areaProjectConsTask = areaProjectConsTaskRepository.save(areaProjectConsTask);
                if (areaProjectConsTask.getStatus().equals("1")) {
                    projectConsTaskList.add(areaProjectConsTask);
                }
            }
        }
        return projectConsTaskList;
    }
}
