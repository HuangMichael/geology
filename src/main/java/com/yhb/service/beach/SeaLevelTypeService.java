package com.yhb.service.beach;

import com.yhb.dao.beach.SeaLevelTypeRepository;
import com.yhb.domain.beach.SeaLevelType;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 */
@Service
public class SeaLevelTypeService extends BaseService {
    @Autowired
    SeaLevelTypeRepository seaLevelTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param seaLevelType
     * @return 保存潮位类型信息
     */
    public SeaLevelType save(SeaLevelType seaLevelType) {
        return seaLevelTypeRepository.save(seaLevelType);
    }

    /**
     * @param id 根据ID删除潮位类型信息
     */
    public ReturnObject delete(Long id) {
        SeaLevelType seaLevelType = seaLevelTypeRepository.findOne(id);
        ReturnObject returnObject;
        if (seaLevelType == null) {
            returnObject = commonDataService.getReturnType(seaLevelType != null, "", "id为" + id + "的潮位类型信息不存在,请确认！");
            return returnObject;
        }

        try {
            seaLevelTypeRepository.delete(seaLevelType);
            //再查询一次查看是否删除
            SeaLevelType coastLine1 = seaLevelTypeRepository.findOne(id);
            returnObject = commonDataService.getReturnType(coastLine1 == null, "潮位类型信息删除成功！", "潮位类型信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            returnObject = commonDataService.getReturnType(false, "", "潮位类型信息删除失败！");
        }
        return returnObject;
    }

    /**
     * @param id
     * @return 根据id查询潮位类型信息
     */
    public SeaLevelType findById(Long id) {
        return seaLevelTypeRepository.findOne(id);
    }

    /**
     * @return 查询所有的潮位类型信息
     */
    public List<SeaLevelType> findAll() {
        return seaLevelTypeRepository.findAll();
    }
}
