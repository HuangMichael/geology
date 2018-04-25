package com.yhb.domain.location;

/**
 * Created by Administrator on 2017/6/7 0007.
 * 位置信息视图
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "V_LOCATION_TREE")
@Data
public class LocationTree implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //序号
    private Long pId; //父节点序号
    private String name; //位置名称
    private String t;   //位置描述
    private String authKey;//授权码

}
