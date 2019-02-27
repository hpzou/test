package com.anshare.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "log")
public class Log {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 操作人
     */
    @Column(name = "UserID")
    private String userid;

    /**
     * 操作时间
     */
    @Column(name = "OptTime")
    private String opttime;

    @Column(name = "IsDeleted")
    private Boolean isdeleted;

    @Column(name = "Timestamp")
    private Date timestamp;

    @Column(name = "IP")
    private String ip;

    /**
     * 操作
     */
    @Column(name = "Region")
    private String region;

    /**
     * 方法
     */
    @Column(name = "Account")
    private String account;

    /**
     * 结果类型（0成功，1失败）
     */
    @Column(name = "OptType")
    private String opttype;

    /**
     * 参数
     */
    @Column(name = "Content")
    private String content;

    /**
     * 参数
     */
    @Column(name = "ResultContent")
    private String resultcontent;
}