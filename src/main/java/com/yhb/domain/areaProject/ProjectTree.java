package com.yhb.domain.areaProject;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

/**
 * 区块项目信息表
 *
 * @author lulimin
 */
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "V_PROJECT_TREE")
@Data
public class ProjectTree implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //序号
    private Long pId; //父节点序号
    private String name; //项目名称
    private String t;   //项目描述
    private String authKey;//授权码
    private String status;//审核状态
}
