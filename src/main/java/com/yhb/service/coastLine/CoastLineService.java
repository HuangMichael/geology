package com.yhb.service.coastLine;

import com.yhb.dao.coastLine.CoastLineRepository;
import com.yhb.dao.coastLine.CoastLineTypeRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.coastline.CoastLine;
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
 * 海岸线业务类
 */
@Service
public class CoastLineService extends BaseService {

    @Autowired
    CoastLineRepository coastLineRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CoastLineTypeRepository coastLineTypeIdRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param id
     * @param lineNo
     * @param lineName
     * @param locationId
     * @param lineLength
     * @param coastLineTypeId
     * @param startPoint
     * @param endPoint
     * @param status
     * @return 保存海岸线信息
     */
    public CoastLine save(Long id, String lineNo, String lineName, Long locationId, Double lineLength, Long coastLineTypeId, String startPoint,
                          String endPoint, String status) {
        try {
            CoastLine coastLine = new CoastLine();
            coastLine.setId(id);
            coastLine.setLineNo(lineNo);
            coastLine.setLineName(lineName);
            if (locationId == null) {
                coastLine.setLocation(null);
            } else {
                coastLine.setLocation(locationRepository.findOne(locationId));
            }
            coastLine.setLineLength(lineLength);
            if (coastLineTypeId == null) {
                coastLine.setCoastLineType(null);
            } else {
                coastLine.setCoastLineType(coastLineTypeIdRepository.findOne(coastLineTypeId));
            }
            coastLine.setStartPoint(startPoint);
            coastLine.setEndPoint(endPoint);
            coastLine.setStatus(status);
            return coastLineRepository.save(coastLine);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param coastLineList
     */
    public void deleteInBatch(List coastLineList) {
        coastLineRepository.deleteInBatch(coastLineList);
    }


    /**
     * @param id 根据ID删除海岸线信息
     */
    public ReturnObject delete(Long id) {
        CoastLine coastLine = coastLineRepository.findOne(id);
        if (coastLine == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的海岸线信息不存在,请确认！");
        }
        try {
            coastLineRepository.delete(coastLine);
            //再查询一次查看是否删除
            CoastLine coastLine1 = coastLineRepository.findOne(id);
            return commonDataService.getReturnType(coastLine1 == null, "海岸线信息删除成功！", "海岸线信息删除失败！");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "海岸线信息删除成功！", "海岸线信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询海岸线信息
     */


    public CoastLine findById(Long id) {
        return coastLineRepository.findOne(id);

    }

    /**
     * @return 查询所有的海岸线信息
     */
    public List<CoastLine> findAll() {
        return coastLineRepository.findAll();

    }


    public List<Long> selectAllIds() {
        return coastLineRepository.selectAllIds();

    }


    /**
     * @return 查询最大的id
     */
    public Long findMaxId() {

        return coastLineRepository.findMaxId();
    }

    /**
     * @param selectedIds
     * @return 返回原来未审核而本次审核成功的记录，已审核的就不再审核
     */
    public List<CoastLine> authorizeInBatch(String selectedIds) {
        if (selectedIds.trim().isEmpty()) {
            return null;
        }
        List<CoastLine> coastLineList = new ArrayList<CoastLine>();
        String array[] = selectedIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long id = Long.parseLong(array[i]);
            CoastLine coastLine = coastLineRepository.findOne(id);
            //只有原先未审核的数据才能被添加到列表areaProjectConsList里面，已审核的就不再审核
            if (coastLine != null && coastLine.getStatus().equals("0")) {
                coastLine.setStatus("1");
                coastLine = coastLineRepository.save(coastLine);
                if (coastLine.getStatus().equals("1")) {
                    coastLineList.add(coastLine);
                }
            }
        }
        return coastLineList;
    }
}
