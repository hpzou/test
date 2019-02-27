package com.anshare.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Table(name = "ad_codelist_type")
public class AdCodelistType {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 字典类型名称
     */
    @Column(name = "CodeValue")
    private String codevalue;

    /**
     * 字典类型描述
     */
    @Column(name = "CodeName")
    private String codename;

    /**
     * 排序
     */
    @Column(name = "CodeOrder")
    private Integer codeorder;

    /**
     * 备注
     */
    @Column(name = "Remark")
    private String remark;

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
}