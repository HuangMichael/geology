package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectReportRepository;
import com.yhb.domain.areaProject.VAreaProjectReport;
import com.yhb.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service
public class VAreaProjectReportService extends BaseService {
    @Autowired
    VAreaProjectReportRepository vAreaProjectReportRepository;
    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public VAreaProjectReport findById(Long id) {
        return vAreaProjectReportRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目可行性研究报告信息
     */

    public List<VAreaProjectReport> findAll() {
        return vAreaProjectReportRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return vAreaProjectReportRepository.selectAllIds();
    }
}
