package com.anshare.project.core.Util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
* @ClassName XmlUtil
* @Description XML格式转为map格式工具类
* @Author wukunfan
* @Date 2018/11/14 15:20
* @UpdateUser:
* @UpdateDate:     2018/11/14 15:20
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class XmlUtil {
	public static Map<String , String> xmlToMap(HttpServletRequest request){
        Map<String ,String> map = new HashMap<String , String>();
        try {
            InputStream inputStream =null;
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(inputStream);
            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            for (Element el:elements) {
                map.put(el.getName() , el.getText());
            }
            inputStream.close();
            return map ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
