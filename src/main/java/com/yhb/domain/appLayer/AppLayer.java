package com.yhb.domain.appLayer;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */
import lombok.Data;
import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
/**
 * 应用图层信息表
 *
 * @author lulimin
 */
@Entity
@Table(name = "T_APP_LAYER")
@Data
public class AppLayer  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 45, nullable = false)
    private String appName; //应用名称

    @Column(length = 200, nullable = false)
    private String imgUrl; //图片图层路径

    @Column( nullable = false)
    private Point leftBottomLoc;//左下角的坐标

    @Column( nullable = false)
    private Point rightTopLoc;//右上角的坐标

    @Column(length = 11, nullable = false)
    private Long minZoom; //最小缩放倍数

    @Column(length = 11, nullable = false)
    private Long maxZoom; //最大缩放倍数

    @Column(length = 1, nullable = false)
    private String status; //状态

}