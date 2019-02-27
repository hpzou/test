package com.anshare.project.core.ResultCore;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }
    public static Result genSuccessResult(String message) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message);
    }
    public static Result genSuccessResult(String data,String message) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(message)
        .setData(data);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result genFailCodeResult(int code,String message) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }

    public static Result genSuccessCodeResult(int code) {
        return new Result()
                .setCode(code);
    }

    public static <T> Result<T> genSuccessCodeResult(T data,int code) {
        return new Result()
                .setCode(code)
                .setData(data);
    }

    public static Result genAuthTokenErrResult(String message) {
        return new Result()
                .setCode(ResultCode.UNAUTHORIZED)
                .setMessage(message);
    }
}
