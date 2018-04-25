package com.yhb.domain.person;


/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 人员信息视图
 *
 * @author huangbin
 */
@Entity
@Table(name = "V_PERSON")
@Data
public class VPerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String personName; //人员姓名
    private String gender; //性别
    private String birthDate; //出生年月
    private String depName; //所属部门
    private String status; //状态
}



