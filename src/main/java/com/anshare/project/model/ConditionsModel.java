package com.anshare.project.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
/**
* @ClassName ConditionsModel
* @Description 导出条件实体类
* @Author wukunfan
* @Date 2018/11/19 14:02
* @UpdateUser:
* @UpdateDate:     2018/11/19 14:02
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
public class ConditionsModel {
    /**
     * 第几页
     */
    @NotNull(message = "pageIndex不能为空")
    private Integer pageIndex;
    /**
     * 每页数
     */
    @NotNull(message = "pageSize不能为空")
    private Integer pageSize;

    /**
     * 关联条件
     */
    private String expandCondition="";
    /**
     * 查询条件
     */
    private String searchCondition="";
    /**
     * 排序条件
     */
    private String orderCondition="";
    /**
     * 导出条件
     */
    @NotNull(message = "导出条件exportCondition不能为空")
    private String exportCondition="";

    /**
     * 扩展条件
     */
    private ExpandModel expandModel;
}
