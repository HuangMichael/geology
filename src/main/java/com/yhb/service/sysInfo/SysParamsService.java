package com.yhb.service.sysInfo;

import com.yhb.dao.sysInfo.SysParamsRepository;
import com.yhb.domain.sysInfo.SysParams;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 系统参数业务类
 */
@Service
@CacheConfig
public class SysParamsService extends BaseService {

    @Autowired
    SysParamsRepository sysParamsRepository;
    @Autowired
    CommonDataService commonDataService;

    /**
     * @param paramName 参数名称
     * @return 根据参数名称查询对象
     */

    @Cacheable(key = "'param'+#paramName", value = "paramName")
    public SysParams findByParamName(String paramName) {
        return sysParamsRepository.findByParamName(paramName);
    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return sysParamsRepository.selectAllIds();
    }

    /**
     * @return 查询所有系统参数信息
     */
    public List<SysParams> findAll() {
        return sysParamsRepository.findAll();
    }

    /**
     * @param id
     * @return 根据ID查询系统参数信息
     */
    public SysParams findById(Long id) {
        return sysParamsRepository.findOne(id);
    }

    /**
     * @param sysParams
     * @return 保存系统参数信息
     */
    public SysParams save(SysParams sysParams) {
        return sysParamsRepository.save(sysParams);
    }

    /**
     * @param id
     * @return 根据ID删除系统参数信息
     */
    public ReturnObject delete(Long id) {
        SysParams sysParams = sysParamsRepository.findOne(id);
        if (sysParams == null) {
            return commonDataService.getReturnType(sysParams != null, "", "id为" + id + "的系统参数信息不存在,请确认！");
        }
        try {
            sysParamsRepository.delete(sysParams);
            //再查询一次查看是否删除
            SysParams sysParams1 = sysParamsRepository.findOne(id);
            return commonDataService.getReturnType(sysParams1 == null, "系统参数信息删除成功！", "系统参数信息删除失败！");
        } catch (EntityNotFoundException e) {
            return commonDataService.getReturnType(true, "系统参数信息删除成功！", "系统参数信息删除失败！");
        }
    }
}
