package com.yhb.service.coastLine;

import com.yhb.dao.coastLine.VCoastLineRepository;
import com.yhb.domain.coastline.VCoastLine;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Service
public class VCoastLineSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VCoastLineRepository vCoastLineRepository;

    /**
     * @param searchPhrase 搜索关键字组合
     * @param paramSize    搜索条件个数
     * @return
     */
    public List<VCoastLine> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vCoastLineRepository.findByLineNoContainsAndLineNameContainsAndIdIn(array[0], array[1],selectedIds);
    }

    /**
     * @param searchPhrase 搜索关键字组合
     * @param paramSize    搜索条件个数
     * @param pageable
     * @return
     */
    public Page<VCoastLine> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return vCoastLineRepository.findByLineNoContainsAndLineNameContains(array[0], array[1], pageable);
    }
}
