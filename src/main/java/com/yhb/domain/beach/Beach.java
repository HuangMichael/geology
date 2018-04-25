package com.yhb.domain.beach;

import com.yhb.domain.location.Location;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 滩涂信息表
 * <p>
 * Created by llm on 2017/7/31.
 */
@Entity
@Table(name = "T_BEACH")
@Data
public class Beach implements Serializable {
    @Id
    @SequenceGenerator(name = "BEACH_GENERATOR", sequenceName = "SEQ_BEACH", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEACH_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, nullable = false)
    private String beachNo;//滩涂编号

    @Column(length = 50, nullable = false)
    private String beachName;//滩涂名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //滩涂所属行政区划

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sea_level_type_id", referencedColumnName = "id")
    private SeaLevelType seaLevelType;//潮位类型，1.潮下带；2.低潮滩；3.中潮滩；4.高潮滩

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date measureDate; //测算时间

    @Column(length = 10)
    private String inningStatus; //围垦状态：1.已围全部；2.已围部分；3.未围

    @Column(length = 1, nullable = false)
    private String status; //状态
}
