package com.anshare.project.dto;

import lombok.Data;

/**
* @ClassName PowerMatters
* @Description top10单位权利事项饼图实体类
* @Author wukunfan
* @Date 2018/12/11 18:01
* @UpdateUser:
* @UpdateDate:     2018/12/11 18:01
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
public class PowerMatters {
    /**
     * 单位名称
     */
    private String name;
    /**
     * 权利事项总数
     */
    private Long value;
}
