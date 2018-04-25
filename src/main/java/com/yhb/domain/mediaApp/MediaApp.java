package com.yhb.domain.mediaApp;


/**
 * Created by 路丽民 on 2017/5/7 0007.
 */
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
/**
 * 多媒体文件应用关系表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_MEDIA_APP")
@Data
public class MediaApp implements Serializable {
    @Id
    @SequenceGenerator(name = "MEDIA_APP_GENERATOR", sequenceName = "SEQ_MEDIA_APP", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIA_APP_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 11, nullable = false)
    private Long fileId; //多媒体文件

    @Column(length = 20, nullable = false)
    private String appName; //应用名称

    @Column(length = 50, nullable = false)
    private String objectId; //应用对象id
}


