package com.anshare.project.core.Util;

/**
* @ClassName SQLInterception
* @Description 防止sql注入实体类
* @Author wukunfan
* @Date 2018/11/22 15:30
* @UpdateUser:
* @UpdateDate:     2018/11/22 15:30
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class SQLInterception {
    //效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "exec|execute|insert|select|delete|update|count|drop|*|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|+|like'|create|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|" +
                "or|;|--|+|like|//|/|#|replace";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return false;//sql注入
            }
        }
        return true;//sql合法
    }
}
