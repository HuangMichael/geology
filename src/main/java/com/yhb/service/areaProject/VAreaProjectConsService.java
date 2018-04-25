package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectConsRepository;
import com.yhb.domain.areaProject.VAreaProjectCons;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 * 系统管理-基础数据管理-项目建设基本信息视图服务
 */
@Service
public class VAreaProjectConsService extends BaseService {
    @Autowired
    VAreaProjectConsRepository vAreaProjectConsRepository;
    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public VAreaProjectCons findById(Long id) {
        return vAreaProjectConsRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目可行性研究报告信息
     */

    public List<VAreaProjectCons> findAll() {
        return vAreaProjectConsRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return vAreaProjectConsRepository.selectAllIds();
    }
}
