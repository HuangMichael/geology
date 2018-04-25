package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.VNatureReserveBufferRepository;
import com.yhb.domain.natureReserve.VNatureReserveBuffer;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service
public class VNatureReserveBufferService extends BaseService {
    @Autowired
    VNatureReserveBufferRepository vNatureReserveBufferRepository;

    /**
     * @param id
     * @return 根据id查询海岸线信息
     */

    public VNatureReserveBuffer findById(Long id) {
        return vNatureReserveBufferRepository.findOne(id);

    }

    /**
     * @return 查询所有的海岸线信息
     */

    public List<VNatureReserveBuffer> findAll() {
        return vNatureReserveBufferRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return vNatureReserveBufferRepository.selectAllIds();
    }
}
