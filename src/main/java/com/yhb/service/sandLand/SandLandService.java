package com.yhb.service.sandLand;

import com.yhb.dao.location.LocationRepository;
import com.yhb.dao.sandLand.SandLandRepository;
import com.yhb.domain.location.Location;
import com.yhb.domain.sandLand.SandLand;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service
public class SandLandService extends BaseService {

    @Autowired
    SandLandRepository sandLandRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @param position
     * @param locationId 所在行政区划的id
     * @param length
     * @param width
     * @return 保存沿海沙洲信息
     */
    public SandLand save(Long id, String sandNo, String sandName, String position, Long locationId, Double length, Double width, String status) {
        try {
            SandLand sandLand = new SandLand();
            sandLand.setId(id);
            sandLand.setSandNo(sandNo);
            sandLand.setSandName(sandName);
            sandLand.setPosition(position);
            if (locationId == null) {
                sandLand.setLocation(null);
            } else {
                sandLand.setLocation(locationRepository.findOne(locationId));
            }
            sandLand.setLength(length);
            sandLand.setWidth(width);
            sandLand.setStatus(status);
            return sandLandRepository.save(sandLand);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param sandLandList
     */
    public void deleteInBatch(List sandLandList) {
        sandLandRepository.deleteInBatch(sandLandList);
    }


    /**
     * @param id 根据ID删除沿海沙洲信息
     */
    public ReturnObject delete(Long id) {
        SandLand sandLand = sandLandRepository.findOne(id);
        if (sandLand == null) {
            return commonDataService.getReturnType(sandLand != null, "", "id为" + id + "的沿海沙洲信息不存在,请确认！");
        }
        try {
            sandLandRepository.delete(sandLand);
            //再查询一次查看是否删除
            SandLand sandLand1 = sandLandRepository.findOne(id);
            return commonDataService.getReturnType(sandLand1 == null, "沿海沙洲信息删除成功！", "沿海沙洲信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "沿海沙洲信息删除成功！", "沿海沙洲信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询沿海沙洲信息
     */

    public SandLand findById(Long id) {
        return sandLandRepository.findOne(id);
    }

    /**
     * @return 查询所有的沿海沙洲信息
     */

    public List<SandLand> findAll() {
        return sandLandRepository.findAll();

    }

    /**
     * @return 查询所有的id
     */
    public List<Long> selectAllIds() {
        return sandLandRepository.selectAllIds();
    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {

        return sandLandRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<SandLand> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<SandLand> sandLandList = new ArrayList<SandLand>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            SandLand sandLand = sandLandRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (sandLand != null && sandLand.getStatus().equals("0")) {
                sandLand.setStatus("1");
                sandLand = sandLandRepository.save(sandLand);
                if (sandLand.getStatus().equals("1")) {
                    sandLandList.add(sandLand);
                }
            }
        }
        return sandLandList;
    }
}
