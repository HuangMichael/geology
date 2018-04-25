package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.VNatureReserveExperimentRepository;
import com.yhb.domain.natureReserve.VNatureReserveExperiment;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service
public class VNatureReserveExperimentService extends BaseService {
    @Autowired
    VNatureReserveExperimentRepository vNatureReserveExperimentRepository;

    /**
     * @param id
     * @return 根据id查询自然保护试验区信息
     */

    public VNatureReserveExperiment findById(Long id) {
        return vNatureReserveExperimentRepository.findOne(id);

    }

    /**
     * @return 查询所有的自然保护试验区信息
     */

    public List<VNatureReserveExperiment> findAll() {
        return vNatureReserveExperimentRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return vNatureReserveExperimentRepository.selectAllIds();
    }
}
