package com.yhb.domain.log;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by huangbin on 2017/9/8.
 */

@Entity
@Table(name = "T_APP_QUERY_LOG")
@Data
public class AppQueryLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "APP_QUERY_LOG_GENERATOR", sequenceName = "SEQ_APP_QUERY_LOG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_QUERY_LOG_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, nullable = false)
    private String appName; //应用名称

    @Column(length = 100)
    private String queryParams; //查询条件

    @Column(length = 20)
    private String userName; //用户名称

    @Column(length = 20)
    private String ip; //ip地址

    @Column(length = 20)
    private Date accessTime; //获取时间

}
