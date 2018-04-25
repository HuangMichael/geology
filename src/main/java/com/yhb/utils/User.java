package com.yhb.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Administrator on 2017/11/22.
 */
@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;

}
