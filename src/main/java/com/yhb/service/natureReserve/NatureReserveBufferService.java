package com.yhb.service.natureReserve;


import com.yhb.dao.natureReserve.NatureReserveBufferRepository;
import com.yhb.domain.farmLand.FarmLand;
import com.yhb.domain.natureReserve.NatureReserveBuffer;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.vo.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017/5/20 0004.
 * 自然保护核心区业务类
 */
@Service
public class NatureReserveBufferService extends BaseService {

    @Autowired
    NatureReserveBufferRepository natureReserveBufferRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param buffer 自然保护缓冲区信息
     * @return 保存  自然保护缓冲区信息
     */
    public NatureReserveBuffer save(NatureReserveBuffer buffer) {
        return natureReserveBufferRepository.save(buffer);

    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        natureReserveBufferRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除  自然保护缓冲区信息
     */
    public ReturnObject delete(Long id) {
        NatureReserveBuffer buffer = natureReserveBufferRepository.findOne(id);
        if (buffer == null) {
            return commonDataService.getReturnType(buffer != null, "", "id为" + id + "的自然保护缓冲区信息不存在,请确认！");
        }

        try {
            natureReserveBufferRepository.delete(buffer);
            //再查询一次查看是否删除
            NatureReserveBuffer buffer1 = natureReserveBufferRepository.findOne(id);
            return commonDataService.getReturnType(buffer1 == null, "自然保护缓冲区信息删除成功！", "自然保护缓冲区信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "自然保护缓冲区信息删除成功！", "自然保护缓冲区信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询 自然保护缓冲区信息
     */

    public NatureReserveBuffer findById(Long id) {
        return natureReserveBufferRepository.findOne(id);

    }

    /**
     * @return 查询所有的  自然保护缓冲区信息
     */

    public List<NatureReserveBuffer> findAll() {
        return natureReserveBufferRepository.findAll();

    }


    public List<Long> selectAllIds() {
        return natureReserveBufferRepository.selectAllIds();

    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return natureReserveBufferRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<NatureReserveBuffer> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<NatureReserveBuffer> natureReserveBufferList = new ArrayList<NatureReserveBuffer>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            NatureReserveBuffer natureReserveBuffer = natureReserveBufferRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表natureReserveBufferList里面，已审核的就不再审核
            if (natureReserveBuffer != null && natureReserveBuffer.getStatus().equals("0")) {
                natureReserveBuffer.setStatus("1");
                natureReserveBuffer = natureReserveBufferRepository.save(natureReserveBuffer);
                if (natureReserveBuffer.getStatus().equals("1")) {
                    natureReserveBufferList.add(natureReserveBuffer);
                }
            }
        }
        return natureReserveBufferList;
    }
}
