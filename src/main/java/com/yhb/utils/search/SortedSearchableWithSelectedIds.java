package com.yhb.utils.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */
public interface SortedSearchableWithSelectedIds {
        /**
         * 此方法用于把用户选择的记录导出为excel
         *
         * @param searchPhrase 搜索关键字组合
         * @param paramSize    搜索条件个数
         * @param selectedIds  根据用户选择的所有记录id查询
         * @return
         */
        List findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds);

        /**
         * @param searchPhrase 搜索关键字组合
         * @param paramSize    搜索条件个数
         * @param pageable
         * @return
         */
        Page findByConditions(String searchPhrase, int paramSize, Pageable pageable);
}