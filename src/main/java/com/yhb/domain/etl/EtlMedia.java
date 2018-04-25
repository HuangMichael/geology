package com.yhb.domain.etl;

import com.yhb.domain.mediaType.MediaType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/20.
 */
@Entity
@Table(name = "T_ETL_MEDIA")
@Data
public class EtlMedia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ETL_MEDIA_GENERATOR", sequenceName = "SEQ_ETL_MEDIA", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ETL_MEDIA_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 50, nullable = false)
    private String fileName; //多媒体文件名称

    @Column(nullable = false)
    private Long fileSize; //多媒体文件大小，单位为B

    @Column(length = 200, nullable = false)
    private String fileRelativeUrl; //多媒体文件相对路径

    @Column(length = 200, nullable = false)
    private String fileAbsoluteUrl; //多媒体文件绝对路径，精确到盘符

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_type_id", referencedColumnName = "id")
    private MediaType mediaType;//多媒体文件类型：文档、图片、视频

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etl_table_id", referencedColumnName = "id",nullable = false)
    private EtlTable etlTable;//所属基本信息表

    @Column(length = 100)
    private String memo; //备注信息

    @Column(length = 1)
    private String status; //状态
}
