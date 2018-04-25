package com.yhb.domain.beach;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/31.
 * 滩涂信息视图
 */

@Entity
@Table(name = "V_BEACH")
@Data
public class VBeach implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    private String beachNo;//滩涂编号
    private String beachName;//滩涂名称
    private String locName; //滩涂所属行政区划
    private String seaLevelType;//潮位类型，1.潮下带；2.低潮滩；3.中潮滩；4.高潮滩

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date measureDate; //测算时间
    private String inningStatus; //围垦状态：1.已围全部；2.已围部分；3.未围
    private String status; //状态
}
