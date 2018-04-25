package com.yhb.service.building;

import com.yhb.dao.building.HistoryBuildingRepository;
import com.yhb.domain.areaBuilding.HistoryBuilding;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin 2017/5/7.
 * 围垦信息业务类
 */
@Service
public class HistoryBuildingService extends BaseService {

    @Autowired
    HistoryBuildingRepository historyBuildingRepository;


    public HistoryBuilding save(HistoryBuilding historyBuilding) {
        return historyBuildingRepository.save(historyBuilding);
    }

    /**
     * @param id 围垦信息id
     * @return 围垦信息
     */
    public HistoryBuilding findById(Long id) {
        return historyBuildingRepository.findOne(id);
    }


    /**
     * @return 查询所有围垦信息
     */
    public List<HistoryBuilding> findAll() {
        return historyBuildingRepository.findAll();
    }


    /**
     * @param period
     * @return 查询所有围垦信息
     */
    public List<HistoryBuilding> findByPeriodOrderBySortNo(String period) {
        return historyBuildingRepository.findByPeriodOrderBySortNo(period);
    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {

        return historyBuildingRepository.findMaxId();
    }


}
