package com.yhb.domain.department;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */
@Entity
@Table(name = "V_DEPARTMENT")
@Data
public class VDepartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号

    private String depNo; //部门编号
    private String depName; //部门名称
    private String depDesc; //部门描述
    private String manager; //部门主管
    private Long depLevel; //部门级别

    private String locName; //所在市名称
    private String parentDepName; //上级部门名称
    private String status; //状态
}
