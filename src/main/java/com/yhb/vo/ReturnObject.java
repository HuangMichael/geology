package com.yhb.vo;

import lombok.Data;

/**
 * Created by huangbin on 2017/5/5 0005.
 * 返回值对象
 */
@Data
public class ReturnObject {
    private Boolean result; //是否成功
    private String resultDesc; //返回描述
    private Object value; //返回值列表
}
