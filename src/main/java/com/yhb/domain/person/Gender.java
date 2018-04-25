package com.yhb.domain.person;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_GENDER")
@Data
public class Gender implements Serializable {
    @Id
    @SequenceGenerator(name = "GENDER_GENERATOR", sequenceName = "SEQ_GENDER", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GENDER_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 2, nullable = false)
    private String value; //性别

}
