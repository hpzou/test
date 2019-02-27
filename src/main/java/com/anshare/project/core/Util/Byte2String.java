package com.anshare.project.core.Util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
* @ClassName Byte2String
* @Description 图片压缩工具类
* @Author wukunfan
* @Date 2018/11/14 16:33
* @UpdateUser:
* @UpdateDate:     2018/11/14 16:33
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class Byte2String {

    public static String fileImage(String str) {
        byte[] bytes = new byte[0];
        try {
            bytes = Base64Util.decodeString(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte [] buf = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            Thumbnails.of(new ByteArrayInputStream(bytes)).scale(1f).outputQuality(0.5f).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buf = outputStream.toByteArray();
        try {
            str = Base64Util.encodeByte(FastDFSClient.rarImage(buf));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }
}
