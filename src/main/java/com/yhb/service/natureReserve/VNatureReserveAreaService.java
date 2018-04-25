package com.yhb.service.natureReserve;

import com.yhb.dao.natureReserve.VNatureReserveAreaRepository;
import com.yhb.domain.natureReserve.VNatureReserveArea;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 */
@Service
public class VNatureReserveAreaService  extends BaseService {
    @Autowired
    VNatureReserveAreaRepository vNatureReserveAreaRepository;

    /**
     * @param id
     * @return 根据id查询自然保护核心区信息
     */

    public VNatureReserveArea findById(Long id) {
        return vNatureReserveAreaRepository.findOne(id);

    }

    /**
     * @return 查询所有的自然保护核心区信息
     */

    public List<VNatureReserveArea> findAll() {
        return vNatureReserveAreaRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return vNatureReserveAreaRepository.selectAllIds();
    }
}
