package com.yhb.service.areaPlan;

import com.yhb.dao.areaPlan.AreaPlanTypeRepository;
import com.yhb.domain.areaPlanType.AreaPlanType;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin 2017/5/7.
 */
@Service
public class AreaPlanTypeService extends BaseService {


    @Autowired
    AreaPlanTypeRepository areaPlanTypeRepository;

    /**
     * @param areaPlanType 围垦计划类型
     * @return 保存围垦计划类型信息
     */
    public AreaPlanType save(AreaPlanType areaPlanType) {
        return areaPlanTypeRepository.save(areaPlanType);

    }

    /**
     * @param id 围垦计划类型id
     * @return 围垦计划类型
     */
    public AreaPlanType findById(Long id) {
        return areaPlanTypeRepository.findOne(id);

    }

    /**
     * @return 查询所有的区块围垦规划类型
     */
    public List<AreaPlanType> findAll() {
        return areaPlanTypeRepository.findAll();
    }

    /**
     * @param id 围垦计划类型id
     * @return
     */
    public Boolean delete(Long id) {
        areaPlanTypeRepository.delete(id);
        return areaPlanTypeRepository.findOne(id) != null;

    }
}
