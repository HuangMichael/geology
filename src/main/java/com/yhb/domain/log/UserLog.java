package com.yhb.domain.log;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户操作日志信息表
 *
 * @author HUANGBIN
 */
@Entity
@Table(name = "T_USER_LOG")
@Data
public class UserLog implements Serializable {
    @Id
    @SequenceGenerator(name = "USER_LOG_GENERATOR", sequenceName = "SEQ_USER_LOG", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_LOG_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 45, nullable = false)
    private String userName; //用户名

    @Column(length = 200, nullable = false)
    private String operation; //操作描述

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime; //最近操作时间

    @Column(length = 20, nullable = false)
    private String loginIp; //最近登陆ip

    @Column(length = 10, nullable = false)
    private String status; //用户状态


}