package com.anshare.project.core.Util;

/**
* @ClassName ServiceException
* @Description 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
* @Author wukunfan
* @Date 2018/11/14 10:17
* @UpdateUser:
* @UpdateDate:     2018/11/14 10:17
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class ServiceException extends RuntimeException {
    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
