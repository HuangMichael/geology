package com.yhb.domain.media;

import com.yhb.domain.user.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/28.
 * 项目多媒体信息表
 */
@Data
public class BmisMedia implements Serializable {

    @Column(length = 50, nullable = false)
    protected String fileName; //多媒体文件名称

    @Column(nullable = false)
    protected Long fileSize; //多媒体文件大小

    @Column(length = 200, nullable = false)
    protected String fileRelativeUrl; //多媒体文件相对路径

    @Column(length = 200, nullable = false)
    protected String fileAbsoluteUrl; //多媒体文件绝对路径，精确到盘符

    @Column(length = 100)
    protected String memo; //备注信息

    @Column(length = 1, nullable = false)
    protected String status; //状态

    @Column(length = 10, nullable = false)
    protected String authKey; //数据权限过滤字段

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    protected User user;//上传多媒体资料的用户

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    protected Date uploadDate; //上传时间精确到时分秒，查询时按照某一天查询

}
