package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.VThirteenFivePlanRepository;
import com.yhb.domain.areaPlan.VThirteenFivePlan;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Service
public class VThirteenFivePlanService extends BaseService {
    @Autowired
    VThirteenFivePlanRepository vThirteenFivePlanRepository;

    /**
     * @param id 围垦计划类型id
     * @return 围垦计划类型
     */
    public VThirteenFivePlan findById(Long id) {
        return vThirteenFivePlanRepository.findOne(id);

    }


    /**
     * @return 查询所有计划
     */
    public List<VThirteenFivePlan> findAll() {
        return vThirteenFivePlanRepository.findAll();
    }

    /**
     * @return 查询所有的ID
     */
    public List<Long> selectAllIds() {
        return vThirteenFivePlanRepository.selectAllIds();
    }

    /**
     * @return 查询某区块的规划的用地类型统计数据求和，并按照区块编号分组
     */
    public List<Object> findAreaTypeSizeGroupByAreaNo(List<Long> idList) {
        return vThirteenFivePlanRepository.findAreaTypeSizeGroupByAreaNo(idList);
    }
}
