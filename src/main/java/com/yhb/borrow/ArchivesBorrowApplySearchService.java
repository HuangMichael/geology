package com.yhb.borrow;

import com.yhb.service.base.BaseService;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin  on 2018/4/25.
 */

@Service
public class ArchivesBorrowApplySearchService extends BaseService implements SortedSearchableWithSelectedIds {

    @Autowired
    ArchivesBorrowApplyRepository archivesBorrowApplyRepository;

    /**
     * @param searchPhrase 搜索关键字组合
     * @param paramSize    搜索条件个数
     * @param selectedIds  根据用户选择的所有记录id查询
     * @return
     */
    public List<ArchivesBorrowApply> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return archivesBorrowApplyRepository.findAll();
    }

    /**
     * @param searchPhrase 搜索关键字组合
     * @param paramSize    搜索条件个数
     * @param pageable
     * @return
     */
    public Page<ArchivesBorrowApply> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        return archivesBorrowApplyRepository.findAll(pageable);
    }
}
