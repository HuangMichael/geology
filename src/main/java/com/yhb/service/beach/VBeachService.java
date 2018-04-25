package com.yhb.service.beach;

import com.yhb.dao.beach.VBeachRepository;
import com.yhb.domain.beach.VBeach;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

@Service
public class VBeachService extends BaseService {
    @Autowired
    VBeachRepository vBeachRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @return 根据id查询海岸线信息
     */

    public VBeach findById(Long id) {
        return vBeachRepository.findOne(id);

    }

    /**
     * @return 查询所有的海岸线信息
     */

    public List<VBeach> findAll() {
        return vBeachRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return vBeachRepository.selectAllIds();
    }
}