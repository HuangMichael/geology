package com.yhb.service.geo;

import com.yhb.dao.geo.GeoLayerRepository;
import com.yhb.domain.geoLayer.GeoLayer;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class GeoLayerService extends BaseService {
    @Autowired
    GeoLayerRepository geoLayerRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param geoLayer
     * @return 保存地理图层信息
     */
    public GeoLayer save(GeoLayer geoLayer) {
        return geoLayerRepository.save(geoLayer);
    }

    /**
     * @param id
     * 根据id删除地理图层信息
     */
    public ReturnObject delete(Long id) {
        GeoLayer geoLayer = geoLayerRepository.findOne(id);
        ReturnObject returnObject;
        if (geoLayer == null) {
            returnObject = commonDataService.getReturnType(false, "", "id为" + id + "的图层不存在！");
            return returnObject;
        }else {
            try {
                geoLayerRepository.delete(geoLayer);
                //再查询一次查看是否删除
                GeoLayer geoLayer1 = geoLayerRepository.findOne(id);
                if (geoLayer1 == null) {
                    returnObject = commonDataService.getReturnType(true, "图层信息删除成功!", "");
                } else {
                    returnObject = commonDataService.getReturnType(false, "", "图层信息删除失败!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                returnObject = commonDataService.getReturnType(false, "", "图层信息无法删除，请联系管理员!");
            }
            return returnObject;
        }
    }

    /**
     * @return 查询所有的用户信息信息
     */

    public List<GeoLayer> findAll() {
        return geoLayerRepository.findAll();

    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */
    public GeoLayer findById(Long id) {
        return geoLayerRepository.findOne(id);
    }


    /**
     * @return  选择所有的id列表
     */
    public List<Long> selectAllIds() {
        return geoLayerRepository.selectAllIds();
    }
}
