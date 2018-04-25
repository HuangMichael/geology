package com.yhb.service.media;

import com.yhb.dao.remoteSensorMedia.VRemoteSensorMediaRepository;
import com.yhb.domain.remoteSensorMedia.VRemoteSensorMedia;
import com.yhb.service.base.BaseService;
import com.yhb.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
@Service
public class VRemoteSensorMediaSearchService extends BaseService {
    @Autowired
    VRemoteSensorMediaRepository vRemoteSensorMediaRepository;

    /**
     * @param searchPhrase
     * @param paramSize
     * @return 根据多条件关键字进行查询
     */
    public List<VRemoteSensorMedia> findByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //如果查询条件没有填写上传日期，则不限制日期。填写了就转换后按照上传时间查询当天内的所有上传资料
        Date uploadDateStart = DateUtils.convertStr2DateTimeWithDefault(array[4], "yyyy-MM-dd HH:mm:ss", true);
        Date uploadDateEnd = DateUtils.convertStr2DateTimeWithDefault(array[4], "yyyy-MM-dd HH:mm:ss", false);
        System.out.println("=======查询日期====开始===uploadDateStart：  " + uploadDateStart);
        System.out.println("=======查询日期====结束===uploadDateEnd：  " + uploadDateEnd);
        return vRemoteSensorMediaRepository.findByAuthKeyStartsWithAndLocationCodeStartsWithAndFileNameContainsAndUserNameContainsAndUploadDateGreaterThanEqualAndUploadDateLessThanEqualAndStatusContains
                (array[0], array[1], array[2], array[3], uploadDateStart, uploadDateEnd, array[5]);
    }
}
