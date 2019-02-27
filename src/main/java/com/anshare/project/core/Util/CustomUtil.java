package com.anshare.project.core.Util;

import com.anshare.project.model.ConditionsModel;
import com.anshare.project.model.SearchConditionsModel;

public class CustomUtil {

    /**
     * @Description:  方法名
     * @param 【ConditionsModel实体类转化为SearchConditionsModel实体类】
     * @return 
     * @throws 
     * @author Zhou
     * @UpdateUser: 
     * @UpdateDate:   2019/1/26 16:27
     * @UpdateRemark:   修改内容
     * @date  2019/1/26 16:27
     */
    public static SearchConditionsModel exchange(ConditionsModel conditionsModel){
        SearchConditionsModel searchConditionsModel = new SearchConditionsModel();
        searchConditionsModel.setSearchCondition(conditionsModel.getSearchCondition());
        searchConditionsModel.setPageIndex(conditionsModel.getPageIndex());
        searchConditionsModel.setPageSize(conditionsModel.getPageSize());
        searchConditionsModel.setExpandModel(conditionsModel.getExpandModel());
        searchConditionsModel.setOrderCondition(conditionsModel.getOrderCondition());
        searchConditionsModel.setExpandCondition(conditionsModel.getExpandCondition());
        return searchConditionsModel;
    }

    /**
     * @Description:  方法名
     * @param 【将阿拉伯数字转化为汉字数字】，如“6”-->“六”，“66”-->“六十六”
     * @return
     * @throws
     * @author Zhou
     * @UpdateUser:
     * @UpdateDate:   2019/1/26 16:19
     * @UpdateRemark:   修改内容
     * @date  2019/1/26 16:19
     */
    public static String getGroupName(String groupID){
        String groupName;
        int length = groupID.length();
        String first = groupID.substring(length - 2,length - 1);
        String second = groupID.substring(length - 1);
        if (first.equals("0")){
            first = "";
        }else if (first.equals("1")){
            first = "十";
        }else if (first.equals("2")){
            first = "二十";
        }else if (first.equals("3")){
            first = "三十";
        }else if (first.equals("4")){
            first = "四十";
        }else if (first.equals("5")){
            first = "五十";
        }else if (first.equals("6")){
            first = "六十";
        }else if (first.equals("7")){
            first = "七十";
        }else if (first.equals("8")){
            first = "八十";
        }else if (first.equals("9")){
            first = "九十";
        }
        if (second.equals("0")){
            second = "";
        }else if (second.equals("1")){
            second =  "一";
        }else if (second.equals("2")){
            second =  "二";
        }else if (second.equals("3")){
            second =  "三";
        }else if (second.equals("4")){
            second =  "四";
        }else if (second.equals("5")){
            second =  "五";
        }else if (second.equals("6")){
            second =  "六";
        }else if (second.equals("7")){
            second =  "七";
        }else if (second.equals("8")){
            second =  "八";
        }else if (second.equals("9")){
            second =  "九";
        }
        groupName  = first + second;
        return groupName;
    }

    /**
     * @Description:  方法名
     * @param 【截取到汉字数字并将其转化为阿拉伯数字】，如“十二届市委第六轮”-->“6”，“巡察六组”-->“6”
     * @return 
     * @throws 
     * @author Zhou
     * @UpdateUser: 
     * @UpdateDate:   2019/1/26 16:25
     * @UpdateRemark:   修改内容
     * @date  2019/1/26 16:25
     */
    public static String getNumberChange(String str){
        int start = 0;
        int end = 0;
        String number = "";
        if (str.contains("届市委")){
            end = str.indexOf("届");
        }else if (str.contains("巡察")){
            start = str.indexOf("察");
            end = str.indexOf("组");
        }else if (str.contains("轮")){
            start = str.indexOf("第");
            end = str.indexOf("轮");
        }else {
            return "输入格式错误";
        }
        if (start == 0){
            number = str.substring(start,end);
        }else {
            number = str.substring(start + 1, end);
        }
        if (number.length() == 1){
            if(number.equals("十")){
                number = "10";
            }else {
                number = change(number);
            }
        }else if (number.length() == 2){
            if (number.endsWith("十")){
                number = change(number) + "0";
            }else {
                number = "1" + change(number);
            }
        }else if (number.length() == 3){
            String first = number.substring(0,1);
            String third = number.substring(2);
            number = change(first) + change(third);
        }else {
            return "输入格式错误";
        }
        return number;
    }

    private static String change(String str){
        if (str.contains("一")){
            str = "1";
        }else if(str.contains("二")){
            str = "2";
        }else if(str.contains("三")){
            str = "3";
        }else if(str.contains("四")){
            str = "4";
        }else if(str.contains("五")){
            str = "5";
        }else if(str.contains("六")){
            str = "6";
        }else if(str.contains("七")){
            str = "7";
        }else if(str.contains("八")){
            str = "8";
        }else if(str.contains("九")){
            str = "9";
        }
        return str;
    }

}
