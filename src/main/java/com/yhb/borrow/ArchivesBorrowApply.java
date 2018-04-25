package com.yhb.borrow;

import com.yhb.archives.Archives;
import com.yhb.domain.mediaCatalog.MediaTree;
import com.yhb.domain.user.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 档案借阅申请信息
 * created  on 2018/4/25
 *
 * @author huangbin
 */
@Entity
@Table(name = "T_ARCHIVES_BORROW_APPLY")
@Data
public class ArchivesBorrowApply implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ARCHIVES_BORROW_APPLY_GENERATOR", sequenceName = "SEQ_ARCHIVES_BORROW_APPLY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARCHIVES_BORROW_APPLY_GENERATOR")
    @Column(name = "id", length = 11, nullable = false)
    private Long id; //序号

    @Column(length = 20, unique = true, nullable = false)
    private String borrowNo; //借阅流水编号


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id", referencedColumnName = "id", nullable = false)
    private User borrower; //档案


    @Column(length = 200)
    private String reason; //借阅原因


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date beginDate; //借阅开始时间


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date endDate; //借阅结束时间


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.DATE)
    private Date borrowDate; //借阅时间

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archives_id", referencedColumnName = "id", nullable = false)
    private Archives archives; //档案


    @Column(length = 1, nullable = false)
    private String status; //状态


    @Column(length = 20, nullable = false, updatable = false)
    private String authKey; //数据权限过滤字段
}
