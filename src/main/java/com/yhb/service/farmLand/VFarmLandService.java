package com.yhb.service.farmLand;

import com.yhb.dao.farmLand.FarmLandRepository;
import com.yhb.dao.farmLand.VFarmLandRepository;
import com.yhb.domain.farmLand.FarmLand;
import com.yhb.domain.farmLand.VFarmLand;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13.
 */
@Service
public class VFarmLandService extends BaseService {
    @Autowired
    VFarmLandRepository vFarmLandRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @return 根据id查询海岸线信息
     */

    public VFarmLand findById(Long id) {
        return vFarmLandRepository.findOne(id);

    }

    /**
     * @return 查询所有的海岸线信息
     */

    public List<VFarmLand> findAll() {
        return vFarmLandRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return vFarmLandRepository.selectAllIds();
    }
}
