package com.yhb.service.areaProject;


import com.yhb.dao.areaProject.AreaProjectReportRepository;
import com.yhb.dao.areaProject.AreaProjectRepository;
import com.yhb.domain.areaProject.AreaProjectReport;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017/5/22 0004.
 * 项目可行性研究报告信息业务类
 */
@Service
public class AreaProjectReportService extends BaseService {

    @Autowired
    AreaProjectReportRepository areaProjectReportRepository;

    @Autowired
    AreaProjectRepository areaProjectRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param dykeLevel
     * @param designStandard
     * @param dykeLineSetting
     * @param dykeSectionalType
     * @param dykeTopHeight
     * @param dykeTopWidth
     * @param slopeRatio
     * @param investedSum
     * @param investedProvince
     * @param investedCity
     * @param investedSelf
     * @param status
     * @return 保存项目可行性研究报告信息
     */
    public AreaProjectReport save(Long id, Long projectId,
                                  String beginDate, String endDate,
                                  String dykeLevel, String designStandard, String dykeLineSetting,
                                  String dykeSectionalType, String dykeTopHeight, String dykeTopWidth,
                                  String slopeRatio, Double investedSum, Double investedProvince,
                                  Double investedCity, Double investedSelf, String status) {
        if (projectId == null) {
            return null;
        }
        AreaProjectReport areaProjectReport = new AreaProjectReport();
        areaProjectReport.setId(id);
        areaProjectReport.setProject(areaProjectRepository.findOne(projectId));
        areaProjectReport.setBeginDate(DateUtils.convertStr2DateWithDefault(beginDate, "yyyy-MM-dd", true));
        areaProjectReport.setEndDate(DateUtils.convertStr2DateWithDefault(endDate, "yyyy-MM-dd", false));
        areaProjectReport.setDykeLevel(dykeLevel);
        areaProjectReport.setDesignStandard(designStandard);
        areaProjectReport.setDykeLineSetting(dykeLineSetting);
        areaProjectReport.setDykeSectionalType(dykeSectionalType);
        areaProjectReport.setDykeTopHeight(dykeTopHeight);
        areaProjectReport.setDykeTopWidth(dykeTopWidth);
        areaProjectReport.setSlopeRatio(slopeRatio);
        areaProjectReport.setInvestedSum(investedSum);
        areaProjectReport.setInvestedProvince(investedProvince);
        areaProjectReport.setInvestedCity(investedCity);
        areaProjectReport.setInvestedSelf(investedSelf);
        areaProjectReport.setStatus(status);


//        areaProjectReport.setAuthKey(authKey);
        return areaProjectReportRepository.save(areaProjectReport);
    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        areaProjectReportRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除项目可行性研究报告信息
     */
    public ReturnObject delete(Long id) {
        AreaProjectReport areaProjectReport = areaProjectReportRepository.findOne(id);
        if (areaProjectReport == null) {
            return commonDataService.getReturnType(areaProjectReport != null, "", "id为" + id + "的可行性研究报告不存在,请确认！");
        }
        try {
            areaProjectReportRepository.delete(areaProjectReport);
            //再查询一次查看是否删除
            AreaProjectReport areaProjectReport1 = areaProjectReportRepository.findOne(id);
            return commonDataService.getReturnType(areaProjectReport1 == null, "可行性研究报告信息删除成功!", "可行性研究报告信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "可行性研究报告信息删除成功！", "可行性研究报告信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public AreaProjectReport findById(Long id) {
        return areaProjectReportRepository.findOne(id);

    }

    /**
     * @return 查询所有的项目可行性研究报告信息
     */

    public List<AreaProjectReport> findAll() {
        return areaProjectReportRepository.findAll();

    }


    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaProjectReportRepository.selectAllIds();
    }

    /**
     * @param projectReportIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<AreaProjectReport> authorizeInBatch(String projectReportIds) {
        if (projectReportIds.trim().isEmpty()) {
            return null;
        }
        List<AreaProjectReport> areaProjectReportList = new ArrayList<AreaProjectReport>();
        String array[] = projectReportIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            AreaProjectReport areaProjectReport = areaProjectReportRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectReportList里面，已审核的就不再审核
            if (areaProjectReport != null && areaProjectReport.getStatus().equals("0")) {
                areaProjectReport.setStatus("1");
                areaProjectReport = areaProjectReportRepository.save(areaProjectReport);
                if (areaProjectReport.getStatus().equals("1")) {
                    areaProjectReportList.add(areaProjectReport);
                }
            }
        }
        return areaProjectReportList;
    }
}
