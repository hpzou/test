package com.anshare.project.core.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

import org.json.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;


/**
* @ClassName SmsUtil
* @Description 调用腾讯短信服务工具类
* @Author wukunfan
* @Date 2018/11/14 14:58
* @UpdateUser:
* @UpdateDate:     2018/11/14 14:58
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Component
public class SmsUtil {
	public static int appId;// 短信应用SDK AppID
	public static String appkey ;// 短信应用SDK AppKey
	public static int templateId ;// 短信模板ID，需要在短信应用中申请
	
	@Value("${sms.appId}")
	public void setAppId(int appId) {
		SmsUtil.appId = appId;
	}
	
	@Value("${sms.appkey}")
	public void setAppkey(String appkey) {
		SmsUtil.appkey = appkey;
	}
	
	@Value("${sms.templateId}")
	public void setTemplateId(int templateId) {
		SmsUtil.templateId = templateId;
	}
	
	/**
	 * 指定模板ID单发短信
	 * @param phoneNumber
	 * @param params
	 * @return
	 */
	public static SmsSingleSenderResult singleSend(String phoneNumber, String[] params) {
		try {
		    SmsSingleSender ssender = new SmsSingleSender(appId, appkey);
		    SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
		        templateId, params, "安夏科技", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
		    return result;
		} catch (HTTPException e) {
		    e.printStackTrace();
		} catch (JSONException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return null;
	}
	
}
