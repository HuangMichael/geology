package com.yhb.domain.mediaType;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
/**
 * 多媒体类型信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_MEDIA_TYPE")
@Data
public class MediaType implements Serializable {
    @Id
    @SequenceGenerator(name = "MEDIA_TYPE_GENERATOR", sequenceName = "SEQ_MEDIA_TYPE", allocationSize = 1, initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEDIA_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, nullable = false)
    private String typeName; //多媒体类型名称

    @Column(length = 45)
    private String memo; //备注信息

    @Column(length = 1,nullable = false)
    private String status; //状态
}


