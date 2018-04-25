package com.yhb.service.area;

import com.yhb.dao.area.AreaTypeRepository;
import com.yhb.domain.area.AreaType;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
@Service
public class AreaTypeService extends BaseService {
    @Autowired
    AreaTypeRepository areaTypeRepository;

    @Autowired
    CommonDataService commonDataService;
    /**
     * @param areaType
     * @return 保存围垦区块功能类型信息
     */
    public AreaType save(AreaType areaType) {
        return areaTypeRepository.save(areaType);
    }

    /**
     * @return 查询所有的区块功能类型
     */
    public List<AreaType> findAll() {
        return areaTypeRepository.findAll();
    }

    /**
     * @param id 对象id
     * @return 根据id查询区块功能类型信息
     */
    public AreaType findById(Long id) {
        return areaTypeRepository.findOne(id);
    }

    /**
     * @param id
     * @return 根据id删除区块功能类型信息
     */
    public ReturnObject delete(Long id) {
        AreaType areaType = areaTypeRepository.findOne(id);

        if (areaType == null) {
            return commonDataService.getReturnType(areaType != null, "", "id为" + id + "的区块功能类型信息不存在,请确认！");
        }

        try {
            areaTypeRepository.delete(areaType);
            //再查询一次查看是否删除
            AreaType areaType1 = areaTypeRepository.findOne(id);
            return commonDataService.getReturnType(areaType1 == null, "区块功能类型信息删除成功！", "区块功能类型信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return commonDataService.getReturnType(false, "", "区块功能类型信息删除失败！");
        }
    }

    /**
     * @return 选择所有的id列表
     */
    public List<Long> selectAllIds() {
        return areaTypeRepository.selectAllIds();
    }
}
