package com.yhb.domain.areaBuilding;

import com.yhb.domain.area.Area;
import com.yhb.domain.location.Location;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 区块已围统计信息视图
 *
 * @author HUANGBIN
 */
@Entity
@Table(name = "V_AREA_BUILT_STAT")
@Data
public class VAreaBuiltStat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private Area area; //所在区块
    private Double buildSize;
    private Double nyBuildSize;
    private Double jsBuildSize;
    private Double stBuildSize;

}
