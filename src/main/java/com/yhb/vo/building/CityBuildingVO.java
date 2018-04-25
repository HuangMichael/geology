package com.yhb.vo.building;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangbin on 2017/5/7.
 * 区块边界值对象
 */
@Data
public class CityBuildingVO implements Serializable {
    private Long id;
    private String placeName;
    private Double nyBuildSize;
    private Double jsBuildSize;
    private Double stBuildSize;
}
