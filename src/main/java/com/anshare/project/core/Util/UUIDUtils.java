package com.anshare.project.core.Util;

import java.util.UUID;

/**
* @ClassName UUIDUtils
* @Description UUID工具类
* @Author wukunfan
* @Date 2018/11/14 15:20
* @UpdateUser:
* @UpdateDate:     2018/11/14 15:20
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class UUIDUtils {

    //格式化的UUID，不包含"-"，32位
    public static String getUUIDTrim(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    //格式化的UUID，包含"-"，36位
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

}
