/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.anshare.project.core.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import lombok.extern.slf4j.Slf4j;

/**
* @ClassName HttpClientUtils
* @Description HTTP客户端工具类（支持HTTPS）
* @Author wukunfan
* @Date 2018/11/14 15:19
* @UpdateUser:
* @UpdateDate:     2018/11/14 15:19
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Slf4j
public class HttpClientUtils {
	
	/**
	 * http的get请求
	 * @param url
	 */
	public static String get(String url) {
		return get(url, "UTF-8");
	}
	
	/**
	 * http的get请求
	 * @param url
	 */
	public static String get(String url, String charset) {
		HttpGet httpGet = new HttpGet(url);
		return executeRequest(httpGet, charset);
	}
	
	/**
	 * http的get请求，增加异步请求头参数
	 * @param url
	 */
	public static String ajaxGet(String url) {
		return ajaxGet(url, "UTF-8");
	}
	
	/**
	 * http的get请求，增加异步请求头参数
	 * @param url
	 */
	public static String ajaxGet(String url, String charset) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
		return executeRequest(httpGet, charset);
	}
	

	/**
	 * http的post请求，传递map格式参数
	 */
	public static String post(String url, Map<String, String> dataMap) {
		return post(url, dataMap, "UTF-8");
	}
	

	/**
	 * http的post请求，传递map格式参数
	 */
	public static String post(String url, Map<String, String> dataMap, String charset) {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, charset);
	}
	
	/**
	 * http的post请求，传递map格式参数
	 */
	public static String postNew(String url, Map<String, String> dataMap, String charset, String header) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("signature", header);
		httpPost.setHeader("content-type", "application/json");
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, charset);
	}

	/**
	 * http的post请求，增加异步请求头参数，传递map格式参数
	 */
	public static String ajaxPost(String url, Map<String, String> dataMap) {
		return ajaxPost(url, dataMap, "UTF-8");
	}

	/**
	 * http的post请求，增加异步请求头参数，传递map格式参数
	 */
	public static String ajaxPost(String url, Map<String, String> dataMap, String charset) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
		try {
			if (dataMap != null){
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
				formEntity.setContentEncoding(charset);
				httpPost.setEntity(formEntity);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return executeRequest(httpPost, charset);
	}

	/**
	 * http的post请求，增加异步请求头参数，传递json格式参数
	 */
	public static String ajaxPostJson(String url, String jsonString) {
		return ajaxPostJson(url, jsonString, "UTF-8");
	}

	/**
	 * http的post请求，增加异步请求头参数，传递json格式参数
	 */
	public static String ajaxPostJson(String url, String jsonString, String charset) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
//		try {
			StringEntity stringEntity = new StringEntity(jsonString, charset);// 解决中文乱码问题
			stringEntity.setContentEncoding(charset);
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		return executeRequest(httpPost, charset);
	}

	/**
	 * 执行一个http请求，传递HttpGet或HttpPost参数
	 */
	public static String executeRequest(HttpUriRequest httpRequest) {
		return executeRequest(httpRequest, "UTF-8");
	}

	/**
	 * 执行一个http请求，传递HttpGet或HttpPost参数
	 */
	public static String executeRequest(HttpUriRequest httpRequest, String charset) {
		CloseableHttpClient httpclient;
		if ("https".equals(httpRequest.getURI().getScheme())){
			httpclient = createSSLInsecureClient();
		}else{
			httpclient = HttpClients.createDefault();
		}
		String result = "";
		try {
			try {
				CloseableHttpResponse response = httpclient.execute(httpRequest);
				HttpEntity entity = null;
				try {
					entity = response.getEntity();
					result = EntityUtils.toString(entity, charset);
				} finally {
					EntityUtils.consume(entity);
					response.close();
				}
			} finally {
				httpclient.close();
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 创建 SSL连接
	 */
	public static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static String post(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			} else {
				log.error("请求返回:" + state + "(" + url + ")");
			}
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 调用http的通用post方法
	 * @return
	 */
	public static String api4Http(String url,String requestData){
        log.info("api4Http----------------------------");
		//实例化httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//实例化post方法
		HttpPost httpPost = new HttpPost(url);
		//处理参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("requestData",requestData));
		log.info("咨询建议接口参数={}");
		//请求结果
		CloseableHttpResponse response = null;
		String content ="";
		try {
			//提交的参数
			UrlEncodedFormEntity uefEntity  = new UrlEncodedFormEntity(nvps, "UTF-8");
			//将参数给post方法
			httpPost.setEntity(uefEntity);
			//执行post方法
			response = httpclient.execute(httpPost);
			if(response != null && response.getStatusLine().getStatusCode()==200){
				content = EntityUtils.toString(response.getEntity(),"utf-8");

			}
			log.info("信息========================="+content);
		} catch (ClientProtocolException e) {
		    log.error("咨询建议异常");
		} catch (IOException e) {
            log.error("咨询建议异常");
		}finally{
			if(httpclient != null){
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}


	/**
	 * ws连接测试 get请求
	 * @param url
	 * @return
	 */
	public static String api4Get(String url){

		//实例化httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//实例化get方法
		HttpGet httpget = new HttpGet(url);
		//请求结果
		CloseableHttpResponse response = null;
		String content ="";
		try {
			//执行get方法
			response = httpclient.execute(httpget);
			if(response !=null && response.getStatusLine().getStatusCode()==200){
				content = EntityUtils.toString(response.getEntity(),"utf-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(httpclient != null){
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}
}
