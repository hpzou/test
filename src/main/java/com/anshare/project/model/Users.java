package com.anshare.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name="users")
public class Users {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "DeptID")
    private String deptid;

    @Column(name = "RoleID")
    private String roleid;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "RealName")
    private String realname;

    @Column(name = "Rank")
    private Integer rank;

    /**
     * 记录删除标志 [0]-未删除;[1]-逻辑删除
     */
    @Column(name = "IsDeleted")
    private Boolean isdeleted;

    /**
     * 创建时间
     */
    @Column(name = "Timestamp")
    private Date timestamp;

    @Transient
    private String deptname;

    @Transient
    private String rolename;
}