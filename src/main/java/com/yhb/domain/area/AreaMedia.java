package com.yhb.domain.area;

import com.yhb.domain.mediaCatalog.MediaTree;
import com.yhb.domain.mediaType.MediaType;
import com.yhb.domain.user.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 区块围垦多媒体信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_AREA_MEDIA")
@Data
public class AreaMedia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AREA_MEDIA_GENERATOR", sequenceName = "SEQ_AREA_MEDIA", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_MEDIA_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 50, nullable = false)
    private String fileName; //多媒体文件名称

    @Column(nullable = false)
    private Long fileSize; //多媒体文件大小，单位为B

    @Column(length = 200, nullable = false)
    private String fileRelativeUrl; //多媒体文件相对路径

    @Column(length = 200)
    private String fileAbsoluteUrl; //多媒体文件绝对路径，精确到盘符

    @Column(length = 200)
    private String thumbNailUrl; //缩略图地址

    @ManyToOne
    @JoinColumn(name = "media_types_id", referencedColumnName = "id", nullable = false)
    private MediaType mediaTypes;//多媒体文件类型：文档、图片、视频


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_tree_id", referencedColumnName = "id", nullable = false)
    private MediaTree mediaTree; //多媒体树id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id", nullable = false)
    private Area area;//所属区块

    @Column(length = 100)
    private String memo; //备注信息

    @Column(length = 1, nullable = false)
    private String status; //状态

    @Column(length = 10, nullable = false, updatable = false)
    private String authKey; //数据权限过滤字段

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;//上传多媒体资料的用户

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date uploadDate; //上传时间精确到时分秒，查询时按照某一天查询

//    @Column(length = 1, nullable = false)
//    private String validStatus; //文件的有效状态，删除文件时将此状态设置为0，而不真正的删除文件，文件上传成功时初始化为1。
}
