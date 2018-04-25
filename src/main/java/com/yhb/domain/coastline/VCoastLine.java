package com.yhb.domain.coastline;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/31.
 */
@Entity
@Table(name = "V_COAST_LINE")
@Data
public class VCoastLine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号

    private String lineNo; //海岸线编号
    private String lineName; //海岸线名称
    private String locName; //海岸线所属行政区划
    private Double lineLength; //海岸线长度
    private String coastLineType;//海岸基本类型名称
    private String startPoint;//起点所在地名称
    private String endPoint;//终点所在地名称
    private String status; //状态
}
