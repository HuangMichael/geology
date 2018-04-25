package com.yhb.domain.person;


/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.yhb.domain.department.Department;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_PERSON")
@Data
public class Person implements Serializable {
    @Id
    @SequenceGenerator(name = "PERSON_GENERATOR", sequenceName = "SEQ_PERSON", allocationSize = 1, initialValue = 12)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String personName; //人员姓名

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender; //性别

    //hibernate jpa 注解 @Temporal(TemporalType.DATE) 格式化时间日期，页面直接得到格式化类型的值，如：2011-04-12
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate; //出生年月

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dep_id", referencedColumnName = "id")
    private Department department; //所属部门

    @Column(length = 1)
    private String status; //状态
}



