package com.yhb.service.user;

import com.yhb.dao.user.VUserRepository;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.service.etl.EtlTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 用户信息业务类
 */
@Service
public class VUserService extends BaseService {
    @Autowired
    VUserRepository vUserRepository;

    @Autowired
    EtlTableService etlTableService;

    /**
     * @return 查询所有ID
     */
    public List<Long> selectAllIds() {
        return vUserRepository.selectAllIds();
    }


    /**
     * @param serviceTableName
     * @param request
     * @param response
     * @return
     */
    public boolean oneKeyExport(String serviceTableName, HttpServletRequest request, HttpServletResponse response) {
        log.info("oneKeyExport export empty table ------------------" + serviceTableName);
        return etlTableService.createExcelTemplate(serviceTableName, request, response);

    }
    /**
     * @param serviceTableName
     */
    public void oneKeyImport(String serviceTableName) {
        log.info("oneKeyExport------------------" + serviceTableName);
    }


}
