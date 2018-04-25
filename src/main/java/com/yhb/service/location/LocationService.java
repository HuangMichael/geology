package com.yhb.service.location;

import com.yhb.dao.location.LocationRepository;
import com.yhb.dao.location.LocationTreeRepository;
import com.yhb.domain.area.Area;
import com.yhb.domain.location.Location;
import com.yhb.domain.location.LocationTree;
import com.yhb.service.base.BaseService;
import com.yhb.service.common.CommonDataService;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.area.Area4Sel;
import com.yhb.vo.location.Location4Sel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
@Service
@CacheConfig
public class LocationService extends BaseService {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CommonDataService commonDataService;


    @Autowired
    LocationTreeRepository locationTreeRepository;

    /**
     * @param location 位置信息
     * @return 保存位置信息
     */
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    /**
     * @param id
     * @return 根据id查询位置信息
     */

    public Location findById(Long id) {
        return locationRepository.findOne(id);
    }


    /**
     * @return 查询可供下拉选择的省市信息
     */
    public List<Location4Sel> findAll4Sel() {
        List<Location4Sel> location4SelList = new ArrayList<>();
        for (Location location : locationRepository.findByLocLevelLessThan(2l)) {
            location4SelList.add(new Location4Sel(location.getId(), location.getLocCode(), location.getLocName()));
        }
        return location4SelList;
    }


    /**
     * @param id
     * @return 该位置的父节点
     */
    public Location findParentById(Long id) {
        Location l = locationRepository.findOne(id);
        return l.getParent();
    }

    /**
     * @param locCode 该位置的位置编码
     * @return 该位置的父节点
     */
    public Location findParentByLocCode(String locCode) {
        if (locCode == null || locCode.trim() == "" || locCode.trim().length() <= 2) {
            return null;
        }
        String pLocCode = locCode.substring(0, locCode.trim().length() - 2);
        return locationRepository.findByLocCode(pLocCode);
    }

    /**
     * @return 根据位置编码locCode查询位置列表
     */

    public List<Location> findLocsByLocCode(String locCode) {
        return locationRepository.findByLocCodeStartsWith(locCode);
    }

    /**
     * @param pId 父节点的id
     * @return
     */
    public List<Location> findLocsByParentId(Long pId) {
        return locationRepository.findByParentIdOrderByLocCodeAsc(pId);
    }

    /**
     * @param level
     * @return 查询所有的位置级别小于level的位置列表
     */
    public List<Location> findByLocLevelLessThan(Long level) {
        return locationRepository.findByLocLevelLessThan(level);
    }

    /**
     * @param level
     * @return 查询位置级别等于该level的所有位置列表
     */
    public List<Location> findByLocLevel(Long level) {
        return locationRepository.findByLocLevel(level);
    }

    /**
     * @param locName
     * @return 根据位置名称查询
     */
    public Location findByLocName(String locName) {
        return locationRepository.findByLocName(locName);
    }

    /**
     * @return 查询所有的位置信息
     */
    @Cacheable(value = "locations", key = "'locations'")
    public List<Location> findAll() {
        return locationRepository.findAll();
    }


    /**
     * @return 查询所有的位置信息
     */
    @Cacheable(value = "locationTree", key = "'locationTree'")
    public List<LocationTree> findTreeByAuthKey(HttpSession session) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return locationTreeRepository.findByAuthKeyStartsWith(authKey);
    }

    /**
     * @param id 根据ID删除位置信息
     */

    public ReturnObject delete(Long id) {
        Location location = locationRepository.findOne(id);
        ReturnObject returnObject;
        if (location == null) {
            returnObject = commonDataService.getReturnType(location != null, "", "id为" + id + "的位置不存在,请确认！");
        } else {
            locationRepository.delete(location);
            //再查询一次查看是否删除
            Location location1 = locationRepository.findOne(id);
            returnObject = commonDataService.getReturnType(location1 == null, "位置信息删除成功！", "位置信删除失败！");
        }
        return returnObject;
    }

}
