package com.yhb.domain.coastline;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/31.
 */
@Entity
@Table(name = "T_COAST_LINE_TYPE")
@Data
public class CoastLineType implements Serializable {
    @Id
    @SequenceGenerator(name = "COAST_LINE_TYPE_GENERATOR", sequenceName = "SEQ_COAST_LINE_TYPE", allocationSize = 1, initialValue = 5)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COAST_LINE_TYPE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 50, nullable = false)
    private String typeName; //海岸线类型名称，选择一种：1.基岩海岸2.粉砂淤泥质海岸3.砂质海岸4.其他

    @Column(length = 1, nullable = false)
    private String status; //状态
}
