package com.yhb.domain.mediaCatalog;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
/**
 * 多媒体库信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_MEDIA_CATALOG")
@Data
public class MediaCatalog implements Serializable {
    @Id
    @SequenceGenerator(name = "MEDIA_CATALOG_GENERATOR", sequenceName = "SEQ_MEDIA_CATALOG", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIA_CATALOG_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String fileName; //多媒体文件名称

    @Column(length = 20, nullable = false)
    private String fileSize; //多媒体文件大小

    @Column(length = 50, nullable = false)
    private String fileUrl; //多媒体文件路径


    @Column(length = 45)
    private String memo; //备注信息

    @Column(length = 1)
    private String status; //状态
}


