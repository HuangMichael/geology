package com.yhb.service.landUsingType;

import com.yhb.dao.landUsingType.LandUsingTypeRepository;
import com.yhb.domain.types.LandUsingType;
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
public class LandUsingTypeService extends BaseService {
    @Autowired
    LandUsingTypeRepository landUsingTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @return 查询所有的用地类型
     */
    public List<LandUsingType> findAll() {
        return landUsingTypeRepository.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询用地类型信息
     */
    public LandUsingType findById(Long id) {
        return landUsingTypeRepository.findOne(id);
    }

    /**
     * @param id
     * @return 根据id删除用地类型信息
     */
    public ReturnObject delete(Long id) {
        LandUsingType landUsingType = landUsingTypeRepository.findOne(id);
        if (landUsingType == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的用地类型信息不存在,请确认！");
        }

        try {
            landUsingTypeRepository.delete(landUsingType);
            //再查询一次查看是否删除
            LandUsingType landUsingType1 = landUsingTypeRepository.getOne(id);
            return commonDataService.getReturnType(landUsingType1 == null, "用地类型信息删除成功！", "用地类型信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "", "用地类型信息删除失败！");
        }
    }
}
