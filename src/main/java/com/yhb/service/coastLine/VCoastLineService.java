package com.yhb.service.coastLine;

import com.yhb.dao.coastLine.VCoastLineRepository;
import com.yhb.domain.coastline.VCoastLine;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Service
public class VCoastLineService extends BaseService {
    @Autowired
    VCoastLineRepository vCoastLineRepository;



    /**
     * @param id
     * @return 根据id查询海岸线视图信息
     */

    public VCoastLine findById(Long id) {
        return vCoastLineRepository.findOne(id);

    }

    /**
     * @return 查询所有的海岸线视图信息
     */

    public List<VCoastLine> findAll() {
        return vCoastLineRepository.findAll();

    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return vCoastLineRepository.selectAllIds();

    }
}
