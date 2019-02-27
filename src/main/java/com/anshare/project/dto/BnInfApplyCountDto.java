package com.anshare.project.dto;

import lombok.Data;

/**
* @ClassName BnInfApplyCountDto
* @Description 历史办件总数/超期办件总数/异常结果办件总数折线图
* @Author wukunfan
* @Date 2018/12/11 18:00
* @UpdateUser:
* @UpdateDate:     2018/12/11 18:00
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Data
public class BnInfApplyCountDto {
    /**
     * 历史办件总数
     */
    private Long[] bnInfApplyTotal;

    /**
     * 超期办件总数
     */
    private Long[] bnInfApplyOverdueTotal;

    /**
     * 异常结果办件总数
     */
    private Long[] bnInfApplyResultDenyTotal;
}
