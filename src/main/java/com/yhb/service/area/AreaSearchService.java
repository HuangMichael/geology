package com.yhb.service.area;

import com.yhb.dao.area.AreaRepository;
import com.yhb.domain.area.Area;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2016/4/14.
 */

@Service
public class AreaSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    AreaRepository areaRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字以及用户选择的id列表进行查询
     */
    public List<Area> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaRepository.findByAuthKeyStartsWithAndAreaNoContainsAndAreaNameContainsAndIdIn(array[0], array[1],array[2],selectedIds);
    }

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<Area> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return areaRepository.findByAuthKeyStartsWithAndAreaNoContainsAndAreaNameContains(array[0], array[1],array[2], pageable);
    }
}
