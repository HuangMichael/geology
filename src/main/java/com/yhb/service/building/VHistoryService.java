package com.yhb.service.building;

import com.yhb.dao.building.VHistoryRepository;
import com.yhb.domain.history.VHistory;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
@Service
public class VHistoryService extends BaseService {

    @Autowired
    VHistoryRepository vHistoryRepository;

    /**
     * @return
     */
    public List<VHistory> findAll(){
        return vHistoryRepository.findAll();
    }
}
