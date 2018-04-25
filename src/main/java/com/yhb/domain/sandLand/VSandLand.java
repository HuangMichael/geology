package com.yhb.domain.sandLand;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/31.
 * 沿海沙洲视图信息
 */
@Entity
@Table(name = "V_SAND_LAND")
@Data
public class VSandLand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id; //序号
    private String sandNo; //沙洲编号
    private String sandName; //沙洲名称
    private String position;//所在位置，文字描述
    private String locName; //所属行政区划
    private Double length; //南北长
    private Double width; //东西宽
    private String status; //状态
}
