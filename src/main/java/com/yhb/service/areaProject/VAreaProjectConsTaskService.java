package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectConsTaskRepository;
import com.yhb.domain.areaProject.VAreaProjectConsTask;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */
@Service
public class VAreaProjectConsTaskService extends BaseService {
    @Autowired
    VAreaProjectConsTaskRepository vAreaProjectConsTaskRepository;

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public VAreaProjectConsTask findById(Long id) {
        return vAreaProjectConsTaskRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目可行性研究报告信息
     */

    public List<VAreaProjectConsTask> findAll() {
        return vAreaProjectConsTaskRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return vAreaProjectConsTaskRepository.selectAllIds();
    }
}
