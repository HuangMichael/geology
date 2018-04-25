package com.yhb.utils.search;

import com.yhb.domain.location.Location;
import com.yhb.service.location.LocationService;
import com.yhb.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

/**
 * Created by huangbin on 2017-08-21 19:17:32
 */
@Slf4j
public class DataFilterUtils {

    /**
     * @param locCode      位置编码
     * @param searchPhrase 查询搜索字符串
     * @param paramsNum    参数个数
     * @return
     */
    public static String transFormSearchStringWithLocCode(String locCode, String searchPhrase, int paramsNum) {
        String authKey = locCode;
        if (searchPhrase.trim().isEmpty()) {
            searchPhrase = authKey + StringUtils.strCopy(",", paramsNum);
        } else {
            searchPhrase = authKey + "," + searchPhrase;//authKey放在searchPhrase前，是第一个参数
        }
        return searchPhrase;
    }

    /**
     * @param session
     * @param searchPhrase 搜索字符串
     * @param paramsNum    查询参数个数  （包含authKey）
     * @return 获取搜索字符串
     */
    public static String getSearchString(HttpSession session, String searchPhrase, int paramsNum) {
        String locCode = (String) session.getAttribute("locCode");
        return transFormSearchStringWithLocCode(locCode, searchPhrase, paramsNum);
    }

    /**
     * @param session
     * @return 根据session获取授权码
     */
    public static String getAuthKey(HttpSession session) {
        String locCode = (String) session.getAttribute("locCode");
        return locCode;
    }
}
