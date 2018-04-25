package com.yhb.service.area;

import com.yhb.dao.area.VAreaRepository;
import com.yhb.domain.area.VArea;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin  on 2016/4/14.
 */

@Service
public class VAreaSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    VAreaRepository vAreaRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VArea> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vAreaRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContainsAndIdInOrderById
                (array[0], array[1], array[2], array[3], array[4], array[5], selectedIds);//array[5]为审核状态，是已审核还是未审核
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VArea> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vAreaRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], array[5], pageable);
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询符合条件的所有区块编号列表
     */
    public List<String> getAreaNoListByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        List<VArea> vAreaList = vAreaRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndAreaNoContainsAndAreaNameContainsAndStatusContains
                (array[0], array[1], array[2], array[3], array[4], array[5]);
        if (vAreaList == null || vAreaList.size() == 0) {
            return null;
        }
        List<String> resList = new ArrayList<>();
        for (int i = 0; i < vAreaList.size(); i++) {
            resList.add(vAreaList.get(i).getAreaNo());
        }
        System.out.println("==vArea===resList======" + resList);
        return resList;
    }
}
