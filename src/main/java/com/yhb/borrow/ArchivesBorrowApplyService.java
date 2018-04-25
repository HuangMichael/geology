package com.yhb.borrow;

import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2018/4/25 0004.
 * 地质档案借阅申请业务类
 */
@Service
@CacheConfig
public class ArchivesBorrowApplyService extends BaseService {

    @Autowired
    ArchivesBorrowApplyRepository archivesBorrowApplyRepository;
    @Autowired
    CommonDataService commonDataService;
    /**
     * @param archivesBorrowApply 地质档案借阅申请
     * @return 保存地质档案信息
     */
    public ReturnObject save(ArchivesBorrowApply archivesBorrowApply) {
        archivesBorrowApply = archivesBorrowApplyRepository.save(archivesBorrowApply);
        return commonDataService.getReturnType(archivesBorrowApply != null, "", "");
    }

    /**
     * @param idList
     */
    public void deleteInBatch(List idList) {
        archivesBorrowApplyRepository.deleteInBatch(idList);
    }


    /**
     * @param id 根据ID删除地质档案借阅申请
     */
    public ReturnObject delete(Long id) {
        return null;
    }

    /**
     * @param id
     * @return 根据id查询地质档案借阅申请
     */

    public ArchivesBorrowApply findById(Long id) {
        return archivesBorrowApplyRepository.findOne(id);

    }
}