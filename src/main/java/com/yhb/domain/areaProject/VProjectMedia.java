package com.yhb.domain.areaProject;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/15.
 * 项目多媒体信息视图
 */
@Entity
@Table(name = "V_PROJECT_MEDIA")
@Data
public class VProjectMedia implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id; //序号

    private String fileName; //多媒体文件名称
    private Long fileSize; //多媒体文件大小
    private String fileRelativeUrl; //多媒体文件相对路径
    private String fileAbsoluteUrl; //多媒体文件绝对路径，精确到盘符
    private String thumbNailUrl; //缩略图地址
    private String mediaTypesName;//多媒体文件类型：文档、图片、视频
    private String projectName;//所属项目名称
    private String memo; //备注信息
    private String status; //状态
    private String authKey; //数据权限过滤字段
    private String userName;//上传多媒体资料的用户名称
    private String treeNodeCode;//目录编码
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date uploadDate; //上传时间精确到时分秒，查询时按照某一天查询

}
