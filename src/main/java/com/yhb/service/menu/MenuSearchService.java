package com.yhb.service.menu;

import com.yhb.dao.menu.MenuRepository;
import com.yhb.domain.menu.Menu;
import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchable;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2017年8月13日 11:46:39
 * <p>
 * 复合查询接口类
 */

@Service
public class MenuSearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    MenuRepository menuRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<Menu> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return menuRepository.findByMenuNameContainsAndIdIn(array[0],selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<Menu> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return menuRepository.findByMenuNameContains(array[0], pageable);
    }


}
