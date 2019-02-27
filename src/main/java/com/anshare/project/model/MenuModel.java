package com.anshare.project.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
* @ClassName MenuModel
* @Description 菜单属性实体类
* @Author wukunfan
* @Date 2018/11/12 17:14
* @UpdateUser:
* @UpdateDate:     2018/11/12 17:14
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
public class MenuModel {
    @Resource
    private Meta meta;

    @Id
    @Column(name = "ID")
    @JSONField(serialize=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JSONField(serialize=false)
    @Column(name = "ParentID")
    private String parentid;

    @JSONField(serialize=false)
    @Column(name = "IsDeleted")
    private boolean isdeleted;


    @JSONField(serialize=false)
    @Column(name = "Sequence")
    private Integer sequence;

    @JSONField(serialize=false)
    @Column(name = "Timestamp")
    private Date timestamp;

    @Column(name = "Title")
    private String title;

    @Column(name = "Name")
    private String name;

    @Column(name = "Icon")
    private String icon;

    @Column(name = "Path")
    private String path;

    @Column(name = "Redirect")
    private String redirect;

    @Column(name = "Component")
    private String component;

    private List<MenuModel> children;

    public MenuModel(String id, String pid, String path, String redirect, String name, String component, String icon, String title, Meta meta) {
        this.id = id;
        this.parentid = pid;
        this.path = path;
        this.redirect = redirect;
        this.name = name;
        this.component = component;
        this.icon = icon;
        this.title = title;
        this.meta = meta;
    }
}