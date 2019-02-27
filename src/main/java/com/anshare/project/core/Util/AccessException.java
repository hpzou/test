package com.anshare.project.core.Util;

/**
* @ClassName AccessException
* @Description 权限异常工具类
* @Author wukunfan
* @Date 2018/11/12 17:35
* @UpdateUser:
* @UpdateDate:     2018/11/12 17:35
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class AccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AccessException() {
    }

    public AccessException(String message) {
        super(message);
    }

    public AccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessException(Throwable cause) {
        super(cause);
    }

    public AccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}