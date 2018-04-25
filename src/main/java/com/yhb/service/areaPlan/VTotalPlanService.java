package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.VTotalPlanRepository;
import com.yhb.domain.areaPlan.VTotalPlan;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Service
public class VTotalPlanService extends BaseService {
    @Autowired
    VTotalPlanRepository vTotalPlanRepository;

    /**
     * @param id 围垦计划类型id
     * @return 围垦计划类型
     */
    public VTotalPlan findById(Long id) {
        return vTotalPlanRepository.findOne(id);

    }


    /**
     * @return 查询所有计划
     */
    public List<VTotalPlan> findAll() {
        return vTotalPlanRepository.findAll();
    }

    /**
     * @return 查询所有的ID
     */
    public List<Long> selectAllIds() {
        return vTotalPlanRepository.selectAllIds();
    }

    /**
     * @return 查询某区块的规划的用地类型统计数据求和，并按照区块编号分组
     */
    public List<Object> findAreaTypeSizeGroupByAreaNo(List<Long> idList) {
        return vTotalPlanRepository.findAreaTypeSizeGroupByAreaNo(idList);
    }
}
