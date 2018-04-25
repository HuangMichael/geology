package com.yhb.service.sandLand;

import com.yhb.dao.sandLand.VSandLandRepository;
import com.yhb.domain.sandLand.VSandLand;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service
public class VSandLandService extends BaseService {
    @Autowired
    VSandLandRepository vSandLandRepository;

    /**
     * @param id
     * @return 根据id查询海岸线信息
     */

    public VSandLand findById(Long id) {
        return vSandLandRepository.findOne(id);

    }

    /**
     * @return 查询所有的海岸线信息
     */

    public List<VSandLand> findAll() {
        return vSandLandRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return vSandLandRepository.selectAllIds();
    }
}
