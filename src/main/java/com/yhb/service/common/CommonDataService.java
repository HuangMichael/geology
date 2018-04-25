package com.yhb.service.common;


import com.yhb.service.base.BaseService;
import com.yhb.vo.ReturnObject;
import com.yhb.vo.app.Mercator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by huangbin on 2017年5月9日10:41:25
 */
@SuppressWarnings(value = "unchecked")
@Service
public class CommonDataService extends BaseService {

    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * @param result      返回结果
     * @param successDesc 执行成功后描述
     * @param failureDesc 执行失败时描述
     * @return
     */
    public ReturnObject getReturnType(Boolean result, String successDesc, String failureDesc) {
        ReturnObject returnObject = new ReturnObject();
        String resultDesc = result ? successDesc : failureDesc;
        returnObject.setResult(result);
        returnObject.setResultDesc(resultDesc);
        return returnObject;
    }


    /**
     * @param tableName 表格名称
     * @param id        id
     *                  <p>
     *                  查询出中心  并且将墨卡托投影转换为WGS坐标
     * @return
     */
    public Mercator convertMercator(String tableName, Long id) {
        String sql = "select st_x (st_centroid(shape)) AS x, st_y (st_centroid(shape)) AS y from " + tableName + " where id = " + id;
        log.info(sql);
        List<Map> mapList = (List) jdbcTemplate.queryForList(sql);
        Double x = null, y = null;
        for (Map map : mapList) {
            log.info(map.keySet());
            x = Double.parseDouble(map.get("x").toString());
            y = Double.parseDouble(map.get("y").toString());
        }
        Mercator mercator = new Mercator();
        mercator.setX(x);
        mercator.setY(y);
        return mercator;
    }


}
