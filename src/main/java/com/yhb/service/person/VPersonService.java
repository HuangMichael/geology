package com.yhb.service.person;

import com.yhb.dao.person.VPersonRepository;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangbin on 2017/5/17 0004.
 * 人员信息业务类
 */
@Service
public class VPersonService extends BaseService {

    @Autowired
    VPersonRepository vPersonRepository;

    @Autowired
    CommonDataService commonDataService;


    /**
     * @return 查询所有ID
     */
    public List<Long> selectAllIds() {
        return vPersonRepository.selectAllIds();
    }
}
