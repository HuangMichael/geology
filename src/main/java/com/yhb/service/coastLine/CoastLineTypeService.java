package com.yhb.service.coastLine;

import com.yhb.dao.coastLine.CoastLineTypeRepository;
import com.yhb.domain.coastline.CoastLineType;
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
public class CoastLineTypeService extends BaseService {
    @Autowired
    CoastLineTypeRepository coastLineTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param coastLineType
     * @return 保存海岸线类型信息
     */
    public CoastLineType save(CoastLineType coastLineType) {
        return coastLineTypeRepository.save(coastLineType);
    }

    /**
     * @param id 根据ID删除海岸线类型信息
     */
    public ReturnObject delete(Long id) {
        CoastLineType coastLineType = coastLineTypeRepository.findOne(id);
        ReturnObject returnObject;
        if (coastLineType == null) {
            returnObject = commonDataService.getReturnType(coastLineType != null, "", "id为" + id + "的海岸线类型信息不存在,请确认！");
            return returnObject;
        }

        try {
            coastLineTypeRepository.delete(coastLineType);
            //再查询一次查看是否删除
            CoastLineType coastLine1 = coastLineTypeRepository.findOne(id);
            returnObject = commonDataService.getReturnType(coastLine1 == null, "海岸线类型信息删除成功！", "海岸线类型信息删除失败！");
        } catch (Exception e) {
            e.printStackTrace();
            returnObject = commonDataService.getReturnType(false, "", "海岸线类型信息删除失败！");
        }
        return returnObject;
    }

    /**
     * @param id
     * @return 根据id查询海岸线类型信息
     */
    public CoastLineType findById(Long id) {
        return coastLineTypeRepository.findOne(id);
    }

    /**
     * @return 查询所有的海岸线类型信息
     */
    public List<CoastLineType> findAll() {
        return coastLineTypeRepository.findAll();
    }
}
