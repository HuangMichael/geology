package com.yhb.domain.user;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yhb.domain.location.Location;
import com.yhb.domain.person.Gender;
import com.yhb.domain.person.Person;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_USER")
@Data
public class User implements Serializable {
    @Id
    @SequenceGenerator(name = "USER_GENERATOR", sequenceName = "SEQ_USER", allocationSize = 1, initialValue = 6)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 100, nullable = false)
    private String userName; //用户名

    @Column(length = 50)
    private String personName; //姓名

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", referencedColumnName = "id", nullable = false)
    private Gender gender; //性别

    //hibernate jpa 注解 @Temporal(TemporalType.DATE) 格式化时间日期，页面直接得到格式化类型的值，如：2011-04-12
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate; //出生年月

    @JsonIgnore
    @Column(length = 100, nullable = false)
    private String password; //密码

    @Column(length = 50)
    private String department; //部门

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //用户位置

    @Column(length = 1)
    private String status; //用户状态
}