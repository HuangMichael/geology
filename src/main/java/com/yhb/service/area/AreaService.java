package com.yhb.service.area;

import com.yhb.dao.area.AreaFunctionTypeRepository;
import com.yhb.dao.area.AreaRepository;
import com.yhb.dao.area.AreaTreeRepository;
import com.yhb.dao.area.AreaTypeRepository;
import com.yhb.dao.location.LocationRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.area.AreaTree;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.area.Area4Sel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangbin on 2017/5/4 0004.
 * 围垦区块业务类
 */
@Service
@CacheConfig
@SuppressWarnings("unchecked")
public class AreaService extends BaseService {

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    AreaTreeRepository areaTreeRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AreaFunctionTypeRepository areaFunctionTypeRepository;

    @Autowired
    AreaTypeRepository areaTypeRepository;

    @Autowired
    CommonDataService commonDataService;

    /**
     * @param session
     * @param id
     * @param areaNo
     * @param areaName
     * @param areaDesc
     * @param cityId
     * @param districtId
     * @param avgHeight
     * @param functionTypeId
     * @param areaTypeId
     * @param status
     * @param inningStatus
     * @param buildSize
     * @param inningSize
     * @param locDesc
     * @param mainPurpose
     * @return 保存围垦区块信息
     */
    public Area save(HttpSession session, Long id, String areaNo, String areaName, String areaDesc, Long cityId, Long districtId, Double avgHeight,
                     Long functionTypeId, Long areaTypeId, String status, String inningStatus, Double buildSize, Double inningSize, String locDesc, String mainPurpose) {
        String authKey = DataFilterUtils.getAuthKey(session);
        Area area = new Area();
        area.setId(id);
        area.setAreaNo(areaNo);
        area.setAreaName(areaName);
        area.setAreaDesc(areaDesc);
        if (cityId == null) {
            area.setCity(null);
        } else {
            area.setCity(locationRepository.findOne(cityId));
        }
        if (districtId == null) {
            area.setDistrict(null);
        } else {
            area.setDistrict(locationRepository.findOne(districtId));
        }
        area.setAvgHeight(avgHeight);
        area.setFunctionType(areaFunctionTypeRepository.findOne(functionTypeId));
        area.setAreaType(areaTypeRepository.findOne(areaTypeId));
        area.setStatus(status);
        area.setInningStatus(inningStatus);
        area.setAuthKey(authKey);
//        area.setBuildSize(buildSize);
//        area.setInningSize(inningSize);
        area.setLocDesc(locDesc);
        area.setMainPurpose(mainPurpose);
        return areaRepository.save(area);
    }

    /**
     * @param areaList 批量删除
     */
    public void deleteInBatch(List areaList) {
        areaRepository.deleteInBatch(areaList);
    }


    /**
     * @param id 根据ID删除围垦区块信息
     */
    public ReturnObject delete(Long id) {
        Area area = areaRepository.findOne(id);
        if (area == null) {
            return commonDataService.getReturnType(false, "", "id为" + id + "的区块不存在,请确认！");
        }
        try {
            areaRepository.delete(area);
            //再查询一次查看是否删除
            Area area1 = areaRepository.findOne(id);
            return commonDataService.getReturnType(area1 == null, "区块信息删除成功!", "区块信息删除失败!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return commonDataService.getReturnType(true, "区块信息删除成功！", "区块信息删除失败！");
        }
    }

    /**
     * @param id
     * @return 根据id查询区块信息
     */

    public Area findById(Long id) {
        return areaRepository.findOne(id);

    }

    /**
     * @return 查询所有区块，按照区块编号排序
     */


    public List<Area> findAll() {
        return areaRepository.findAllOrderByAreaNo();

    }


    /**
     * @return 查询可供下拉选择的区块信息
     */
    public List<Area4Sel> findAll4Sel() {
        List<Area4Sel> area4SelList = new ArrayList<>();
        for (Area area : areaRepository.findAllOrderByAreaNo()) {
            area4SelList.add(new Area4Sel(area.getId(), area.getAreaNo(), area.getAreaName()));
        }
        return area4SelList;
    }


    /**
     * @param locId
     * @return 根据位置id查询可供该位置的区块信息
     */
    public List<Area4Sel> findAll4SelByLocId(Long locId) {
        List<Area4Sel> area4SelList = new ArrayList<>();
        for (Area area : areaRepository.findAreasByLocationId(locId)) {
            area4SelList.add(new Area4Sel(area.getId(), area.getAreaNo(), area.getAreaName()));
        }
        return area4SelList;
    }

    /**
     * @return
     */
    public List<Long> selectAllIds() {
        return areaRepository.selectAllIds();

    }


    /**
     * @return 查询区块树
     */
    public List<AreaTree> findAreaTreeByAuthKey(HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return areaTreeRepository.findByAuthKeyStartsWith(authKey);
    }

    /**
     * @param session
     * @param authStatus
     * @return 查询区块树, 而且根据授权码和审核状态查询
     */
    public List<AreaTree> findAreaTreeByAuthKeyAndStatus(HttpSession session, String authStatus) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return areaTreeRepository.findByAuthKeyStartsWithAndStatusContains(authKey, authStatus);
    }

    /**
     * @param areaNo
     * @return 根据区块编号和区块名称查询区块信息
     */
    public Area findByAreaNo(String areaNo) {
        return areaRepository.findByAreaNo(areaNo);
    }

    /**
     * @param id
     * @param areaNo
     * @return 编辑区块信息时，根据前端传过来的用户输入的新的区块编号和id查询是否与其他的重复。如果重复，返回一个Area；不重复返回null
     */

    public Area findAreaDuplicateByNoAndId(Long id, String areaNo) {
        //查询不是该id的其他区块id列表
        List<Long> otherIds = selectOtherIds(id);
        return areaRepository.findByAreaNoAndIdIn(areaNo, otherIds);
    }

    /**
     * @param areaName
     * @return 根据区块名称查询区块信息
     */
    public Area findByAreaName(String areaName) {
        return areaRepository.findByAreaName(areaName);
    }

    /**
     * @param id
     * @param areaName
     * @return 编辑区块信息时，根据前端传过来的用户输入的新的区块名称和id查询是否与其他的重复。如果重复，返回一个Area；不重复返回null
     */
    public Area findAreaDuplicateByNameAndId(Long id, String areaName) {
        //查询不是该id的其他区块id列表
        List<Long> otherIds = selectOtherIds(id);
        return areaRepository.findByAreaNameAndIdIn(areaName, otherIds);
    }

    /**
     * @param id
     * @return 查询不是该id的其他ld列表
     */
    public List<Long> selectOtherIds(Long id) {
        return areaRepository.selectOtherIds(id);
    }

    /**
     * @param areaIds
     * @return
     */
    public List<Area> authorizeInBatch(String areaIds) {
        if (areaIds.trim().isEmpty()) {
            return null;
        }
        List<Area> areaList = new ArrayList<Area>();
        String array[] = areaIds.split(",");
        for (int i = 0; i < array.length; i++) {
            Long areaId = Long.parseLong(array[i]);
            Area area = areaRepository.findOne(areaId);
            if (area != null) {
                area.setStatus("1");
                area = areaRepository.save(area);
                if (area.getStatus().equals("1")) {
                    areaList.add(area);
                }
            }
        }
        return areaList;
    }
}