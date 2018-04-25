package com.yhb.service.engineeringType;

import com.yhb.dao.engineeringType.EngineeringTypeRepository;
import com.yhb.domain.types.EngineeringType;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
public class EngineeringTypeService extends BaseService {
    @Autowired
    EngineeringTypeRepository engineeringTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @return 查询所有的工程类型
     */
    public List<EngineeringType> findAll() {
        return engineeringTypeRepository.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询工程类型信息
     */
    public EngineeringType findById(Long id) {
        return engineeringTypeRepository.findOne(id);
    }

    /**
     * @param id
     * @return 根据id删除工程类型信息
     */
    public ReturnObject delete(Long id) {
        EngineeringType engineeringType = engineeringTypeRepository.findOne(id);
        if (engineeringType == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的工程类型信息不存在,请确认！");
        }

        try {
            engineeringTypeRepository.delete(engineeringType);
            //再查询一次查看是否删除
            EngineeringType engineeringType1 = engineeringTypeRepository.getOne(id);
            return commonDataService.getReturnType(engineeringType1 == null, "工程类型信息删除成功！", "工程类型信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "", "工程类型信息删除失败！");
        }
    }
}
