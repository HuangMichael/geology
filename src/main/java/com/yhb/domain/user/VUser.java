package com.yhb.domain.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/14 0014.
 * 用户信息视图
 */
@Entity
@Table(name = "V_USER")
@Data
public class VUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号
    private String userName; //用户名
    private String password; //密码
    private String gender; //密码
    private Date birthDate;
    private String personName; //关联人员名称
    private String department; //关联人员名称
    private String status; //用户状态
}
