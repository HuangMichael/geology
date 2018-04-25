package com.yhb.service.beach;

import com.yhb.dao.beach.BeachRepository;
import com.yhb.dao.beach.SeaLevelTypeRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.beach.Beach;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.DateUtils;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 * 滩涂业务类
 */
@SuppressWarnings(value = "unchecked")
@Service
public class BeachService extends BaseService {

    @Autowired
    BeachRepository beachRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SeaLevelTypeRepository seaLevelTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @param beachName
     * @param locationId
     * @param seaLevelTypeId
     * @param measureDate
     * @param inningStatus
     * @return 保存滩涂信息
     */
    public Beach save(Long id, String beachNo, String beachName, Long locationId, Long seaLevelTypeId, String measureDate, String inningStatus, String status) {
        try {
            Beach beach = new Beach();
            beach.setId(id);
            beach.setBeachNo(beachNo);
            beach.setBeachName(beachName);
            if (locationId == null) {
                beach.setLocation(null);
            } else {
                beach.setLocation(locationRepository.findOne(locationId));
            }
            if (seaLevelTypeId == null) {
                beach.setSeaLevelType(null);
            } else {
                beach.setSeaLevelType(seaLevelTypeRepository.findOne(seaLevelTypeId));
            }
            beach.setMeasureDate(DateUtils.convertStr2DateTimeWithDefault(measureDate, "yyyy-MM-dd", true));
            beach.setInningStatus(inningStatus);
            beach.setStatus(status);
            return beachRepository.save(beach);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param beachList
     */
    public void deleteInBatch(List beachList) {
        beachRepository.deleteInBatch(beachList);
    }


    /**
     * @param id 根据ID删除滩涂信息
     */
    public ReturnObject delete(Long id) {
        Beach beach = beachRepository.findOne(id);

        if (beach == null) {
            return commonDataService.getReturnType(beach != null, "", "id为" + id + "的滩涂信息不存在,请确认！");
        }

        try {
            beachRepository.delete(beach);
            //再查询一次查看是否删除
            Beach beach1 = beachRepository.findOne(id);
            return commonDataService.getReturnType(beach1 == null, "滩涂信息删除成功！", "滩涂信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "滩涂信息删除成功", "滩涂信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询滩涂信息
     */

    public Beach findById(Long id) {
        return beachRepository.findOne(id);
    }

    /**
     * @return 查询所有的滩涂信息
     */

    public List<Beach> findAll() {
        return beachRepository.findAll();
    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return beachRepository.selectAllIds();
    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return beachRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<Beach> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<Beach> beachList = new ArrayList<Beach>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            Beach beach = beachRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (beach != null && beach.getStatus().equals("0")) {
                beach.setStatus("1");
                beach = beachRepository.save(beach);
                if (beach.getStatus().equals("1")) {
                    beachList.add(beach);
                }
            }
        }
        return beachList;
    }
}
