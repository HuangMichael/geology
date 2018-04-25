package com.yhb.archives;

import com.yhb.dao.area.AreaFunctionTypeRepository;
import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.area.AreaTreeRepository;
import com.yhb.dao.area.AreaTypeRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.area.AreaTree;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.area.Area4Sel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 围垦区块业务类
 */
@Service
@CacheConfig
public class ArchivesService extends BaseService {

    @Autowired
    ArchivesRepository archivesRepository;
    @Autowired
    CommonDataService commonDataService;


    /**
     * @param archives 地质档案
     * @return 保存地质档案信息
     */
    public ReturnObject save(Archives archives) {
        archives = archivesRepository.save(archives);
        return commonDataService.getReturnType(archives != null, "", "");
    }

    /**
     * @param idList
     */
    public void deleteInBatch(List idList) {
        archivesRepository.deleteInBatch(idList);
    }


    /**
     * @param id 根据ID删除围垦区块信息
     */
    public ReturnObject delete(Long id) {
        return null;
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public Archives findById(Long id) {
        return archivesRepository.findOne(id);

    }
}