package com.yhb.domain.user;

/**
 * Created by 路丽民 on 2017/5/7 0007.
 */

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户gis偏好信息信息表
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_USER_PREFERENCE")
@Data
public class UserPreference implements Serializable {
    @Id
    @SequenceGenerator(name = "USER_PREFERENCE_GENERATOR", sequenceName = "SEQ_USER_PREFERENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_PREFERENCE_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 45, nullable = false)
    private String baseMap0; //用户名

    @Column(length = 45, nullable = false)
    private String baseMap1; //用户名

    @Column(length = 1)
    private String status; //用户状态

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //关联用户
}