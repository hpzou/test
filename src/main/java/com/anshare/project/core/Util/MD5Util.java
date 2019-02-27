package com.anshare.project.core.Util;

import java.security.MessageDigest;

/**
* @ClassName MD5Util
* @Description Md5加密工具类
* @Author wukunfan
* @Date 2018/11/14 14:46
* @UpdateUser:
* @UpdateDate:     2018/11/14 14:46
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class MD5Util {

    //加密
    public static String MD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }
}
