package com.yhb.vo.location;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by huangbin on 2017/11/16.
 * <p>
 * 下拉选择的位置信息
 */
@Data
@AllArgsConstructor
public class Location4Sel {
    private long id;
    private String locCode;
    private String locName;
}
