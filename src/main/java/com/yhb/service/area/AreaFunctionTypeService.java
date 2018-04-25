package com.yhb.service.area;

import com.yhb.dao.area.AreaFunctionTypeRepository;
import com.yhb.domain.area.AreaFunctionType;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
@Service
public class AreaFunctionTypeService extends BaseService {

    @Autowired
    AreaFunctionTypeRepository areaFunctionTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param areaFunctionType
     * @return 保存围垦区块功能类型信息
     */
    public AreaFunctionType save(AreaFunctionType areaFunctionType) {
        return areaFunctionTypeRepository.save(areaFunctionType);
    }

    /**
     * @return 查询所有的区块功能类型
     */
    public List<AreaFunctionType> findAll() {
        return areaFunctionTypeRepository.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块功能类型信息
     */
    public AreaFunctionType findById(Long id) {
        return areaFunctionTypeRepository.findOne(id);
    }

    /**
     * @param id
     * @return 根据id删除区块功能类型信息
     */
    public ReturnObject delete(Long id) {
        AreaFunctionType areaFunctionType = areaFunctionTypeRepository.findOne(id);

        if (areaFunctionType == null) {
            return commonDataService.getReturnType(areaFunctionType != null, "", "id为" + id + "的区块功能类型信息不存在,请确认！");
        }

        try {
            areaFunctionTypeRepository.delete(areaFunctionType);
            //再查询一次查看是否删除
            AreaFunctionType areaFunctionType1 = areaFunctionTypeRepository.findOne(id);
            return commonDataService.getReturnType(areaFunctionType1 == null, "区块功能类型信息删除成功！", "区块功能类型信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "", "区块功能类型信息删除失败！");
        }
    }

    /**
     * @return 选择所有的id列表
     */
    public List<Long> selectAllIds() {
        return areaFunctionTypeRepository.selectAllIds();
    }
}