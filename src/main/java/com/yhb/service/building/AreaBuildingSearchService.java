package com.yhb.service.building;

import com.yhb.dao.building.AreaBuildingRepository;
import com.yhb.domain.areaBuilding.AreaBuilding;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin 2017/5/22.
 * 围垦信息复合查询业务类
 */
@Service
public class AreaBuildingSearchService extends BaseService implements SortedSearchable {


    @Autowired
    AreaBuildingRepository  areaBuildingRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<AreaBuilding> findByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaBuildingRepository.findAll();
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<AreaBuilding> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaBuildingRepository.findAll(pageable);
    }

}
