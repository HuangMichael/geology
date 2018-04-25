package com.yhb.service.building;

import com.yhb.dao.building.VHistoryBuildingRepository;
import com.yhb.domain.areaBuilding.VHistoryBuilding;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin 2017/5/7.
 * 历史围垦信息业务类
 */
@Service
public class VHistoryBuildingService extends BaseService {

    @Autowired
    VHistoryBuildingRepository vHistoryBuildingRepository;


    /**
     * @param period
     * @return 查询所有围垦信息
     */
    public List<VHistoryBuilding> findByPeriod(String period) {
        return vHistoryBuildingRepository.findByPeriod(period);
    }


    /**
     * @param
     * @return 查询所有围垦信息
     */
    public List<VHistoryBuilding> findAll() {
        return vHistoryBuildingRepository.findAll();
    }


    /**
     * @param period
     * @return 查询所有围垦信息
     */
    public List<VHistoryBuilding> findByPeriodOrderBySortNo(String period, String locName) {
        return vHistoryBuildingRepository.findByPeriodAndLocName(period, locName);
    }

    /**
     * @return 查询所有的围垦信息，按照年份求和
     */
    public List<Object> findAllGroupByPeriod() {
        return vHistoryBuildingRepository.findAllGroupByPeriod();
    }
}
