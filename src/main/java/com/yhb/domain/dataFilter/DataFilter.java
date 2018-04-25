package com.yhb.domain.dataFilter;

import lombok.Data;

import javax.persistence.*;

/**
 * 数据过滤器
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("dataFilter")
public class DataFilter {

    @Id
    @Column(name = "id", length = 11, nullable = false)
    public Long id; //序号

    @Column(length = 10, nullable = false, updatable = false)
    public String authKey; //数据权限过滤字段
}
