package com.anshare.project.model;

import lombok.Data;
/**
* @ClassName SqlModel
* @Description sql拼接条件实体类
* @Author wukunfan
* @Date 2018/11/28 18:39
* @UpdateUser:
* @UpdateDate:     2018/11/28 18:39
* @UpdateRemark:   修改内容
* @Version 1.0
**/

@Data
public class SqlModel {
    /**
     * 查询条件sql
     */
    private String searchSql ="";

    /**
     * 查询列sql
     */
    private String selectSql ="";
    /**
     * 关联条件sql
     */
    private String relationSql ="";
    /**
     * 排序条件sql
     */
    private String orderSql ="";

    /**
     * 扩展类型
     */
    Object obj ;
}
