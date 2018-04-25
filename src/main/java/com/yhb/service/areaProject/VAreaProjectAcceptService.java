package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectAcceptRepository;
import com.yhb.domain.areaProject.VAreaProjectAccept;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 项目验收业务类
 */
@Service
public class VAreaProjectAcceptService extends BaseService {

    @Autowired
    VAreaProjectAcceptRepository vAreaProjectAcceptRepository;

    public List<Long> selectAllIds() {
        return vAreaProjectAcceptRepository.selectAllIds();
    }

    /**
     * @param id
     * @return 根据id查询项目验收基本信息
     */

    public VAreaProjectAccept findById(Long id) {
        return vAreaProjectAcceptRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目验收基本信息
     */

    public List<VAreaProjectAccept> findAll() {
        return vAreaProjectAcceptRepository.findAll();

    }
}
