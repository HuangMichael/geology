package com.yhb.domain.department;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.yhb.domain.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_DEPARTMENT")
@Data
public class Department implements Serializable {
    @Id
    @SequenceGenerator(name = "DEPARTMENT_GENERATOR", sequenceName = "SEQ_DEPARTMENT", allocationSize = 1, initialValue = 7)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENT_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String depNo; //部门编号

    @Column(length = 45, nullable = false)
    private String depName; //部门名称

    @Column(length = 100)
    private String depDesc; //部门描述

    @Column(length = 20)
    private String manager; //部门主管

    @Column(length = 11)
    private Long depLevel; //部门级别

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //所在市

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Department parent; //上级部门

    @Column(length = 1)
    private String status; //状态
}
