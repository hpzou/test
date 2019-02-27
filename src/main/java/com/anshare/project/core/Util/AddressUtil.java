package com.anshare.project.core.Util;
/**
* @ClassName AddressUtil
* @Description 地区工具类
* @Author wukunfan
* @Date 2018/11/14 15:18
* @UpdateUser:
* @UpdateDate:     2018/11/14 15:18
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class AddressUtil {
    //获取省
    public static String getProvince(String address){
        if("香港特别行政区".startsWith(address)||"澳门特别行政区".startsWith(address)){
            address = address.substring(0, address.indexOf("特别行政区") + 1);
        }else if("北京市".startsWith(address)||"上海市".startsWith(address)||"天津市".startsWith(address)||"重庆市".startsWith(address)){
            address = address.substring(0, address.indexOf("市") + 1);
        }else if("内蒙古自治区".startsWith(address)||"新疆维吾尔自治区".startsWith(address)||"宁夏回族自治区".startsWith(address)||"广西壮族自治区".startsWith(address)||"西藏自治区".startsWith(address)){
            address = address.substring(0, address.indexOf("自治区") + 1);
        }else{
            address = address.substring(0, address.indexOf("省") + 1);
        }
        return address;
    }

    //获取市
    public static String getCity(String address){
        if("香港特别行政区".startsWith(address)||"澳门特别行政区".startsWith(address)){
            address = address.substring(0, address.indexOf("特别行政区") + 1);
        }else if("北京市".startsWith(address)||"上海市".startsWith(address)||"天津市".startsWith(address)||"重庆市".startsWith(address)){
            address = address.substring(0, address.indexOf("市") + 1);
        }else if("内蒙古自治区".startsWith(address)||"新疆维吾尔自治区".startsWith(address)||"宁夏回族自治区".startsWith(address)||"广西壮族自治区".startsWith(address)||"西藏自治区".startsWith(address)){
            address = address.substring(0, address.indexOf("自治区") + 1);
        }else{
            address = address.substring(address.indexOf("省") + 1);
        }
        int indexShi = address.indexOf("市");
        if(indexShi < 0) {
            indexShi = address.indexOf("区");
            if(indexShi < 0) {
                indexShi = address.indexOf("县");
            }
        }
        address = address.substring(0,indexShi + 1);
        return address;
    }

    public static void main(String[] args) {
        String s="江苏省宿迁市宿豫区皂河镇龙岗村二组108号";
        getCity(s);
    }

}
