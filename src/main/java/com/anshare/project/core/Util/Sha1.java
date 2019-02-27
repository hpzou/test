package com.anshare.project.core.Util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
* @ClassName Sha1
* @Description Sha1加密工具类
* @Author wukunfan
* @Date 2018/11/14 14:47
* @UpdateUser:
* @UpdateDate:     2018/11/14 14:47
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class Sha1 {
    private static final String HMAC_SHA1 = "HmacSHA1";
    /**
     * HmacSHA1 加密
     * @author DerrickZheng
     *
     */
    public static byte[] getHashHmac_Sha1(String data, String key) throws Exception {
        Mac mac = Mac.getInstance(HMAC_SHA1);
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                mac.getAlgorithm());
        mac.init(signingKey);
        return mac.doFinal(data.getBytes());
    }

    /**
     * sha1 加密
     * @author DerrickZheng
     */
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            System.out.println("sha1异常:  "+e.getMessage());
            return null;
        }
    }
}