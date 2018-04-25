package com.yhb.domain.user;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/8/22.
 * 给角色、授权等功能添加用户时专用的用户类，包含序号、用户名、人员姓名、部门名称，四个属性。
 */
@Data
public class UserForAddUsers implements Serializable {
    private Long id; //序号
    private String userName; //用户名
    private String personName; //人员姓名
    private String department;//部门名称
}
