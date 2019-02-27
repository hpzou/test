package com.anshare.project.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

/**
* @ClassName Role
* @Description 角色实体类
* @Author wukunfan
* @Date 2018/11/12 16:58
* @UpdateUser:
* @UpdateDate:     2018/11/12 16:58
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
@Table(name = "role")
public class Role {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "RoleAuthName")
    private String roleauthname;

    @Column(name = "HomePage")
    private String homepage;

    @Column(name = "Rank")
    private Integer rank;

    @Column(name = "IsDeleted")
    private Boolean isdeleted;

    @Column(name = "Timestamp")
    private Date timestamp;

    @Column(name = "RoleName")
    private String rolename;
}