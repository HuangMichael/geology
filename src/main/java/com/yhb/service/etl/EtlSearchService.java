package com.yhb.service.etl;

import com.yhb.dao.etl.EtlRepository;
import com.yhb.domain.etl.EtlTableConfig;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service
public class EtlSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    EtlRepository etlRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<EtlTableConfig> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return etlRepository.findByBaseColDescContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<EtlTableConfig> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return etlRepository.findByBaseColDescContains(array[0],pageable);
    }
}
