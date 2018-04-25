package com.yhb.service.natureReserve;


import com.yhb.dao.natureReserve.NatureReserveAreaRepository;
import com.yhb.domain.natureReserve.NatureReserveArea;
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
public class NatureReserveAreaService extends BaseService {

    @Autowired
    NatureReserveAreaRepository natureReserveAreaRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param area 围垦自然保护核心区信息
     * @return 保存围垦自然保护核心区信息
     */
    public NatureReserveArea save(NatureReserveArea area) {
        return natureReserveAreaRepository.save(area);

    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        natureReserveAreaRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除围垦自然保护核心区信息
     */
    public ReturnObject delete(Long id) {
        NatureReserveArea area = natureReserveAreaRepository.findOne(id);
        if (area == null) {
            return commonDataService.getReturnType(area != null, "", "id为" + id + "的自然保护核心区信息不存在,请确认！");
        }

        try {
            natureReserveAreaRepository.delete(area);
            //再查询一次查看是否删除
            NatureReserveArea area1 = natureReserveAreaRepository.findOne(id);
            return commonDataService.getReturnType(area1 == null, "自然保护核心区信息删除成功！", "自然保护核心区信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "自然保护核心区信息删除成功！", "自然保护核心区信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询自然保护核心区信息
     */

    public NatureReserveArea findById(Long id) {
        return natureReserveAreaRepository.findOne(id);

    }

    /**
     * @return 查询所有的自然保护核心区信息
     */

    public List<NatureReserveArea> findAll() {
        return natureReserveAreaRepository.findAll();

    }


    public List<Long> selectAllIds() {
        return natureReserveAreaRepository.selectAllIds();

    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {
        return natureReserveAreaRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<NatureReserveArea> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<NatureReserveArea> natureReserveAreaList = new ArrayList<NatureReserveArea>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            NatureReserveArea natureReserveArea = natureReserveAreaRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (natureReserveArea != null && natureReserveArea.getStatus().equals("0")) {
                natureReserveArea.setStatus("1");
                natureReserveArea = natureReserveAreaRepository.save(natureReserveArea);
                if (natureReserveArea.getStatus().equals("1")) {
                    natureReserveAreaList.add(natureReserveArea);
                }
            }
        }
        return natureReserveAreaList;
    }
}
