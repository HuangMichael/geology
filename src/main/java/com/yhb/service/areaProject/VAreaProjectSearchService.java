package com.yhb.service.areaProject;

import com.yhb.dao.areaProject.VAreaProjectRepository;
import com.yhb.domain.areaProject.VAreaProject;
import com.yhb.service.base.BaseService;
import com.yhb.utils.ConstantUtils;
import com.yhb.utils.DateUtils;
import com.yhb.utils.search.DataFilterUtils;
import com.yhb.utils.search.SortedSearchableWithSelectedIds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
public class VAreaProjectSearchService extends BaseService implements SortedSearchableWithSelectedIds {
    @Autowired
    VAreaProjectRepository vAreaProjectRepository;

    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public List<VAreaProject> findByConditions(String searchPhrase, int paramSize, List<Long> selectedIds) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vAreaProjectRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsAndIdInOrderByImportantDesc(
                array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7], selectedIds);
    }


    /**
     * @param searchPhrase
     * @return 根据多条件关键字进行查询
     */
    public Page<VAreaProject> findByConditions(String searchPhrase, int paramSize, Pageable pageable) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        return vAreaProjectRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsOrderByImportantDesc(
                array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7], pageable);
    }

    /**
     * @param searchPhrase
     * @param paramSize
     * @return 根据多条件关键字进行查询符合条件的项目的id列表
     */
    public List<Long> getIdListByConditions(String searchPhrase, int paramSize) {
        String array[] = super.assembleSearchArray(searchPhrase, paramSize);
        //将开始年份的字符串转换为date格式
        Date beginYear = DateUtils.convertStr2DateWithDefault(array[5], "yyyy-MM-dd", true);
        //将结束年份的字符串转换为date格式
        Date endYear = DateUtils.convertStr2DateWithDefault(array[6], "yyyy-MM-dd", false);
        List<VAreaProject> areaProjectList = vAreaProjectRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndProjectNoContainsAndProjectNameContainsAndBeginYearGreaterThanEqualAndEndYearLessThanEqualAndStatusContainsOrderByImportantDesc
                (array[0], array[1], array[2], array[3], array[4], beginYear, endYear, array[7]);
        if (areaProjectList == null || areaProjectList.size() == 0) {
            return null;
        }
        List<Long> resList = new ArrayList<>();
        for (int i = 0; i < areaProjectList.size(); i++) {
            resList.add(areaProjectList.get(i).getId());
        }
        System.out.println("==vAreaProject===resList======" + resList);
        return resList;
    }

    /**
     * @param session
     * @param authStatus
     * @return
     */
    public List<VAreaProject> findByAuthKeyAndStatus(HttpSession session, @RequestParam("authStatus") String authStatus) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vAreaProjectRepository.findByAuthKeyStartsWithAndStatusContains(authKey, authStatus);
    }

    /**
     * @param session
     * @param cityName
     * @param districtName
     * @param authStatus
     * @return 根据 授权码、市、县区、审核状态 查询项目信息
     */
    public List<VAreaProject> findByAuthKeyAndCityNameAndDistrictNameAndStatus(HttpSession session, String cityName, String districtName, String authStatus) {
        String authKey = DataFilterUtils.getAuthKey(session);
        return vAreaProjectRepository.findByAuthKeyStartsWithAndCityNameContainsAndDistrictNameContainsAndStatusContains(authKey, cityName, districtName, authStatus);
    }
}
