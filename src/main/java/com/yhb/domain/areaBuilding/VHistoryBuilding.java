package com.yhb.domain.areaBuilding;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 围垦历史演变信息视图
 *
 * @author huangbin
 */
@Entity
@Table(name = "V_HISTORY_BUILDING_STAT")
@Data
public class VHistoryBuilding implements Serializable {
    @Id
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号
    private String period; //状态
    private String locName; //所在市
    private Double buildSize; //围垦面积

}
