package com.anshare.project.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
/**
* @ClassName Dynamictables
* @Description 表格设计实体类
* @Author wukunfan
* @Date 2018/11/12 16:11
* @UpdateUser:
* @UpdateDate:     2018/11/12 16:11
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
@Table(name="dynamictables")
public class Dynamictables {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 表名
     */
    @Column(name = "TableName")
    private String tableName;

    /**
     * 数据
     */
    @Column(name = "FormJson")
    private String formJson;

    /**
     * 操作时间
     */
    @Column(name = "Optime")
    private String optime;

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

    /**
     * 使用位置
     */
    @Column(name = "Position")
    private String position;

    /**
     * 备注
     */
    @Column(name = "Remark")
    private String remark;
}