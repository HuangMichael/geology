package com.yhb.service.farmLand;

import com.yhb.dao.farmLand.FarmLandRepository;
import com.yhb.domain.farmLand.FarmLand;
import com.yhb.domain.farmLand.FarmLand;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017/5/20 0004.
 * 耕地业务类
 */
@Service
public class FarmLandService extends BaseService {

    @Autowired
    FarmLandRepository farmLandRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param farmLand
     * @return 保存耕地信息
     */
    public FarmLand save(FarmLand farmLand) {
        return farmLandRepository.save(farmLand);
    }

    /**
     * @param coastLineList
     */
    public ReturnObject deleteInBatch(List coastLineList) {
        // farmLandRepository.deleteInBatch(coastLineList);
        return null;
    }


    /**
     * @param id 根据ID删除耕地信息
     */
    public ReturnObject delete(Long id) {
        FarmLand farmLand = farmLandRepository.findOne(id);
        if (farmLand == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的耕地信息不存在,请确认！");
        }
        try {
            farmLandRepository.delete(farmLand);
            //再查询一次查看是否删除
            FarmLand farmLand1 = farmLandRepository.findOne(id);
            return commonDataService.getReturnType(farmLand1 == null, "耕地信息删除成功！", "耕地信息删除失败！");
        } catch (EntityNotFoundException e) {
            return commonDataService.getReturnType(true, "耕地信息删除成功！", "耕地信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public FarmLand findById(Long id) {
        return farmLandRepository.findOne(id);

    }

    /**
     * @return 查询所有的耕地信息
     */

    public List<FarmLand> findAll() {
        return farmLandRepository.findAll();

    }


    /**
     * @return 选择所有的id列表
     */
    public List<Long> selectAllIds() {
        return farmLandRepository.selectAllIds();
    }



    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return  farmLandRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<FarmLand> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<FarmLand> farmLandList = new ArrayList<FarmLand>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            FarmLand farmLand = farmLandRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (farmLand != null && farmLand.getStatus().equals("0")) {
                farmLand.setStatus("1");
                farmLand = farmLandRepository.save(farmLand);
                if (farmLand.getStatus().equals("1")) {
                    farmLandList.add(farmLand);
                }
            }
        }
        return farmLandList;
    }
}
