package com.yhb.archives;

import com.yhb.domain.mediaCatalog.MediaTree;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 档案信息
 * created  on 2017/10/30
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_ARCHIVES")
@Data
public class Archives implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ARCHIVES_GENERATOR", sequenceName = "SEQ_ARCHIVES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARCHIVES_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, unique = true, nullable = false)
    private String archiveNo; //档案编号

    @Column(length = 20, nullable = false)
    private String shortName; //文档简称

    @Column(length = 100)
    private String name; //档案全称

    @Column(length = 100)
    private String createdBy; //创建人

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date createDate; //创建时间

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_tree_id", referencedColumnName = "id", nullable = false)
    private MediaTree mediaTree; //多媒体树id


    @Column(length = 1, nullable = false)
    private String secClass; //密级


    @Column(length = 1, nullable = false)
    private String status; //状态


    @Column(length = 20, nullable = false, updatable = false)
    private String authKey; //数据权限过滤字段
}
