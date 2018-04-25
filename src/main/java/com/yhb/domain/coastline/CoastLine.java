package com.yhb.domain.coastline;

import com.yhb.domain.location.Location;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 海岸线信息表
 *
 * @author huangbin
 * modifiedBy 路丽民
 */
@Entity
@Table(name = "T_COASTLINE")
@Data
public class CoastLine implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "COAST_LINE_GENERATOR", sequenceName = "SEQ_COAST_LINE", allocationSize = 1, initialValue = 7)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COAST_LINE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 10, unique = true, nullable = false)
    private String lineNo; //海岸线编号

    @Column(length = 50, nullable = false)
    private String lineName; //海岸线名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location; //海岸线所属行政区划

    private Double lineLength; //海岸线长度

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coast_line_type_id", referencedColumnName = "id", nullable = false)
    private CoastLineType coastLineType;//海岸基本类型，选择一种：1.基岩海岸2.粉砂淤泥质海岸3.砂质海岸4.其他

    @Column(length = 50)
    private String startPoint;//起点所在地名称

    @Column(length = 50)
    private String endPoint;//终点所在地名称

    @Column(length = 1, nullable = false)
    private String status; //状态


}
