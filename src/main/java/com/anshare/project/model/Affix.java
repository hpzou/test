package com.anshare.project.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

/**
* @ClassName Affix
* @Description 附件实体类
* @Author wukunfan
* @Date 2018/11/12 14:33
* @UpdateUser:
* @UpdateDate:     2018/11/12 14:33
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
@Table(name = "affix")
public class Affix {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 资源ID
     */
    @Column(name = "ResourceID")
    private String resourceid;

    @Column(name = "Type")
    private String type;

    @Column(name = "IsDeleted")
    private Boolean isdeleted;

    @Column(name = "Timestamp")
    private Date timestamp;

    @Column(name = "FileName")
    private String filename;

    @Column(name = "SaveName")
    private String savename;

    @Column(name = "FileExtension")
    private String fileextension;

}