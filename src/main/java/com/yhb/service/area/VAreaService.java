package com.yhb.service.area;

import com.yhb.dao.area.VAreaRepository;
import com.yhb.domain.area.VArea;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 围垦区块业务类
 */
@Service
public class VAreaService extends BaseService {

    @Autowired
    VAreaRepository vAreaRepository;

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public VArea findById(Long id) {
        return vAreaRepository.findOne(id);

    }

    /**
     * @return 查询所有的围垦区块信息
     */

    public List<VArea> findAll() {
        return vAreaRepository.findAll();

    }


    /**
     * @return 选择所有的id
     */
    public List<Long> selectAllIds(){
        return vAreaRepository.selectAllIds();
    }
}
