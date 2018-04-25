package com.yhb.vo.area;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangbin on 2017/5/7.
 * 区块边界值对象
 */
@Data
public class AreaBoundaryVO implements Serializable {


    private Long id;
    private String boundaryStr;
    private Long areaId;
}
