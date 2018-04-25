package com.yhb.service.sysInfo;

import com.yhb.dao.sysInfo.SysParamsRepository;
import com.yhb.domain.sysInfo.SysParams;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 系统参数业务类
 */
@Service
public class SysParamsSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    SysParamsRepository sysParamsRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<SysParams> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return sysParamsRepository.findByParamDescContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<SysParams> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return sysParamsRepository.findByParamDescContains(array[0], pageable);
    }

}
