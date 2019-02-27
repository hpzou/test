package com.anshare.project.core;

import java.lang.annotation.*;

@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
/**
* @ClassName MyLog
* @Description 自定义注解类
* @Author wukunfan
* @Date 2018/12/4 22:39
* @UpdateUser:
* @UpdateDate:     2018/12/4 22:39
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public @interface MyLog {
    String value() default "";
}
