package com.anshare.project.dto;

import lombok.Data;

/**
* @ClassName GroupUserDto
* @Description 用于导出巡查工作记录数据和当前任务下的巡查员数据
* @Author WKF
* @Date 2019/2/19 15:39
* @UpdateUser:
* @UpdateDate: 2019/2/19 15:39
* @UpdateRemark:   修改内容
* @Version 1.0
*/
@Data
public class GroupUserDto {
    /**--WorkGroup实体类信息--**/
    private String id;

    /**
     * 巡察结束时间
     */
    private String endtime;

    /**
     * 巡察开始时间
     */
    private String starttime;

    /**
     * 轮次
     */
    private String turn;

    /**
     * 巡查组名
     */
    private String groupname;

    /**
     * 组号
     */
    private String groupid;

    /**
     * 被巡察单位
     */
    private String patrolcompany;

    /**
     * 创建时间
     */
    private String timestamp;

    /**
     * 批次id
     */
    private String batchid;

    /**
     * 届次
     */
    private String period;

    /**--Users实体类信息--**/
    private String uid;

    /**
     * 姓名
     */
    private String realname;

    /**
     * 编号
     */
    private String number;

    /**
     * 职务
     */
    private String post;

    /**
     * 电话
     */
    private String phone;

    /**
     * 组号
     */
    private String workgroupid;

    /**
     * 组名
     */
    private String ugroupname;

    /**
     * 批次id
     */
    private String uturn;

    /**
     * 角色id
     */
    private String roleid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private String utimestamp;
}
