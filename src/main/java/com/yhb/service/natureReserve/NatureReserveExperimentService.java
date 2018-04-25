
package com.yhb.service.natureReserve;


import com.yhb.dao.natureReserve.NatureReserveExperimentRepository;
import com.yhb.domain.natureReserve.NatureReserveExperiment;
import com.yhb.domain.natureReserve.NatureReserveExperiment;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijina on 2017/6/22.
 * 自然保护试验区业务类
 */
@Service
public class NatureReserveExperimentService extends BaseService {

    @Autowired
    NatureReserveExperimentRepository natureReserveExperimentRepository;


    @Autowired
    CommonDataService commonDataService;
    /**
     * @param experiment 自然保护试验区信息
     * @return 保存自然保护试验区信息
     */
    public NatureReserveExperiment save(NatureReserveExperiment experiment) {
        return natureReserveExperimentRepository.save(experiment);

    }



    /**
     * @param id 根据ID删除自然保护试验区信息
     */
    public ReturnObject delete(Long id) {
        NatureReserveExperiment experiment = natureReserveExperimentRepository.findOne(id);
        if (experiment == null) {
            return commonDataService.getReturnType(experiment != null, "", "id为" + id + "的自然保护试验区信息不存在,请确认！");
        }
        try {
            natureReserveExperimentRepository.delete(experiment);
            //再查询一次查看是否删除
            NatureReserveExperiment experiment1 = natureReserveExperimentRepository.findOne(id);
            return commonDataService.getReturnType(experiment1 == null, "自然保护试验区信息删除成功！", "自然保护试验区信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "自然保护试验区信息删除成功！", "自然保护试验区信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询自然保护试验区信息
     */

    public NatureReserveExperiment findById(Long id) {
        return natureReserveExperimentRepository.findOne(id);

    }

    /**
     * @return 查询所有的自然保护试验区信息
     */

    public List<NatureReserveExperiment> findAll() {
        return natureReserveExperimentRepository.findAll();

    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return natureReserveExperimentRepository.selectAllIds();
    }



    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return  natureReserveExperimentRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<NatureReserveExperiment> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<NatureReserveExperiment> natureReserveExperimentList = new ArrayList<NatureReserveExperiment>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            NatureReserveExperiment natureReserveExperiment = natureReserveExperimentRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (natureReserveExperiment != null && natureReserveExperiment.getStatus().equals("0")) {
                natureReserveExperiment.setStatus("1");
                natureReserveExperiment = natureReserveExperimentRepository.save(natureReserveExperiment);
                if (natureReserveExperiment.getStatus().equals("1")) {
                    natureReserveExperimentList.add(natureReserveExperiment);
                }
            }
        }
        return natureReserveExperimentList;
    }
}

