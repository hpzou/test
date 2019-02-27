package com.anshare.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
* @ClassName AdCodelist
* @Description 字典实体类
* @Author wukunfan
* @Date 2018/11/12 14:31
* @UpdateUser:
* @UpdateDate:     2018/11/12 14:31
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
@Table(name = "ad_codelist")
public class AdCodelist {
    /**
     * ROW_ID
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 字典值
     */
    @Column(name = "CodeValue")
    private String codevalue;

    /**
     * 字典值描述
     */
    @Column(name = "CodeName")
    private String codename;

    /**
     * 字典类型
     */
    @Column(name = "CodeType")
    private String codetype;

    /**
     * 字典类型名称
     */
    @Column(name = "CodeTypeDict")
    private String codetypedict;

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