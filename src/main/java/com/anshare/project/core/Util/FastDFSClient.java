package com.anshare.project.core.Util;

import org.slf4j.LoggerFactory;
import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

/**
* @ClassName FastDFSClient
* @Description fastDFS工具类
* @Author wukunfan
* @Date 2018/11/14 14:48
* @UpdateUser:
* @UpdateDate:     2018/11/14 14:48
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class FastDFSClient {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
	
	public static byte[] rarImage(byte[] bytes) {
		byte [] buf = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			Thumbnails.of(new ByteArrayInputStream(bytes)).scale(1f).outputQuality(0.5f).toOutputStream(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf = outputStream.toByteArray();
		return buf;
	}
}