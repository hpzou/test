package com.anshare.project.dto;

import lombok.Data;

/**
* @ClassName BnInfCountDto
* @Description 4个值实体类
* @Author wukunfan
* @Date 2018/12/11 17:59
* @UpdateUser:
* @UpdateDate:     2018/12/11 17:59
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
public class BnInfCountDto {
    /**
     * 行政权力事项数量
     */
    private Long bnInfTotal;

    /**
     * 历史办件总数
     */
    private Long bnInfApplyTotal;

    /**
     * 超期办件总数
     */
    private Long bnInfApplyOverdueTotal;

    /**
     * 异常结果办件总数
     */
    private Long bnInfApplyResultDenyTotal;
}
