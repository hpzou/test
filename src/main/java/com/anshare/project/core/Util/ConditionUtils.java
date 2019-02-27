package com.anshare.project.core.Util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.model.ConditionsModel;
import com.anshare.project.model.SearchConditionsModel;
import com.anshare.project.model.SqlModel;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.Map;

/**
* @ClassName ConditionUtils
* @Description 所有List接口查询条件工具类
* @Author wukunfan
* @Date 2018/11/12 17:36
* @UpdateUser:
* @UpdateDate:     2018/11/12 17:36
* @UpdateRemark:   修改内容
* @Version 1.0
**/
@Slf4j
public class ConditionUtils {
    /**
     * @Description: 所有List查询通用方法（支持模糊查询，排序等处理）,仅用作单表处理
     * @param
     * @return Condition
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/11/14 10:20
     * @UpdateRemark:   修改内容
     * @date 2018/11/14 10:20
     */
    public static Condition getConditions(Condition condition,final String searchCondition,String orderCondition){
        Example.Criteria criteria  = condition.createCriteria();
        criteria.andEqualTo("isdeleted", 0);
        if(!searchCondition.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(searchCondition);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strKey = jsonObject.get("field")+"";//查询Key
                    String strType = jsonObject.get("operator")+"";//查询类别
                    if("bet".equals(strType)){//在XX之间
                        String strValue = jsonObject.get("value")+"";//查询条件值
                        criteria.andBetween(strKey,strValue.substring(0,strValue.indexOf("|")),strValue.substring(strValue.indexOf("|")+1));
                        continue;
                    }
                    if("eq".equals(strType)){//等于
                        if(jsonObject.get("value")==null){
                            criteria.andIsNull(strKey);
                        }else{
                            criteria.andEqualTo(strKey, jsonObject.get("value"));
                        }
                        continue;
                    }
                    if("neq".equals(strType)){//不等于
                        criteria.andNotEqualTo(strKey, jsonObject.get("value"));
                        continue;
                    }
                    if("notNull".equals(strType)){//不为空
                        criteria.andIsNotNull(strKey);
                        continue;
                    }
                    if("isNull".equals(strType)){//为空
                        criteria.andIsNull(strKey);
                        continue;
                    }
                    if("gt".equals(strType)){//大于
                        criteria.andGreaterThan(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("lt".equals(strType)){//小于
                        criteria.andLessThan(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("egt".equals(strType)){//大于等于
                        criteria.andGreaterThanOrEqualTo(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("elt".equals(strType)){//小于等于
                        criteria.andLessThanOrEqualTo(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("like".equals(strType)){//模糊查询
                        criteria.andLike(strKey, "%"+jsonObject.get("value")+"%");
                        continue;
                    }
                }
            }
        }
        System.out.println("查询条件是什么"+criteria.toString());
        var orderConditionStr="";
        if(!orderCondition.isEmpty()) {
            orderConditionStr = orderCondition+",";
        }
        System.out.println(orderConditionStr+" timestamp DESC");
        condition.setOrderByClause(orderConditionStr+" timestamp DESC");
        return condition;
    }

    /**
     * @Description: 所有List定制化查询通用方法（支持模糊查询，排序等处理）仅用作单表处理
     * @param
     * @return Condition
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/11/14 10:21
     * @UpdateRemark:   修改内容
     * @date 2018/11/14 10:21
     */
    public static Condition getConditions(Condition condition, Example.Criteria criteria, final String searchCondition,String orderCondition){
        criteria.andEqualTo("isdeleted", 0);
        if(!searchCondition.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(searchCondition);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strKey = jsonObject.get("field")+"";//查询Key
                    String strType = jsonObject.get("operator")+"";//查询类别
                    if("bet".equals(strType)){//在XX之间
                        String strValue = jsonObject.get("value")+"";//查询条件值
                        criteria.andBetween(strKey,strValue.substring(0,strValue.indexOf("|")),strValue.substring(strValue.indexOf("|")+1));
                        continue;
                    }
                    if("eq".equals(strType)){//等于
                        if("".equals(jsonObject.get("value"))){
                            criteria.andEqualTo(strKey, jsonObject.get("value"));
                        }else{
                            criteria.andIsNull(strKey);
                        }
                        continue;
                    }
                    if("neq".equals(strType)){//不等于
                        criteria.andNotEqualTo(strKey, jsonObject.get("value"));
                        continue;
                    }
                    if("notNull".equals(strType)){//不为空
                        criteria.andIsNotNull(strKey);
                        continue;
                    }
                    if("gt".equals(strType)){//大于
                        criteria.andGreaterThan(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("lt".equals(strType)){//小于
                        criteria.andLessThan(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("egt".equals(strType)){//大于等于
                        criteria.andGreaterThanOrEqualTo(strKey,jsonObject.get("value"));
                        continue;
                    }
                    if("like".equals(strType)){//模糊查询
                        criteria.andLike(strKey, "%"+jsonObject.get("value")+"%");
                        continue;
                    }
                    if("elt".equals(strType)){//小于等于
                        criteria.andLessThanOrEqualTo(strKey,jsonObject.get("value"));
                        continue;
                    }
                }
            }
        }
        var orderConditionStr="";
        if(!orderCondition.isEmpty()) {
            orderConditionStr = orderCondition+",";
        }
        System.out.println(orderConditionStr+" timestamp DESC");
        condition.setOrderByClause(orderConditionStr+" timestamp DESC");
        return condition;
    }


    /**
     * @Description: 所有List定制化查询通用方法（支持模糊查询，排序等处理） 支持多表，超级查询
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/11/22 15:11
     * @UpdateRemark:   修改内容
     * @date 2018/11/22 15:11
     */
    public static String getConditions(SearchConditionsModel searchConditionsModel){
        PageHelper.startPage(searchConditionsModel.getPageIndex(),searchConditionsModel.getPageSize());
        String searchCondition = searchConditionsModel.getSearchCondition();

        String relationSql ="";//关联条件
        String searchSql ="";//查询条件
        String relationSearchSql ="";//关联查询条件
        String orderSql ="";//排序条件

        if(!searchCondition.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(searchCondition);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strKey = jsonObject.get("field")+"";//查询Key
                    String strType = jsonObject.get("operator")+"";//查询类别
                    if("eq".equals(strType)){//等于
                        if(jsonObject.get("value")==null){
                            searchSql = searchSql + "" + strKey + " is null" + " AND ";
                        }else{
                            searchSql = searchSql + "" + strKey + " = '" + jsonObject.get("value") + "' AND ";
                        }
                        continue;
                    }
                    if("neq".equals(strType)){//不等于
                        searchSql = searchSql + "" + strKey + " != '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("notNull".equals(strType)){//不为空
                        searchSql = searchSql + "" + strKey + " is not null " + " AND ";
                        continue;
                    }
                    if("gt".equals(strType)){//大于
                        searchSql = searchSql + "" + strKey + " <![CDATA[ > ]]> '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("lt".equals(strType)){//小于
                        searchSql = searchSql + "" + strKey + " <![CDATA[ < ]]> '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("egt".equals(strType)){//大于等于
                        searchSql = searchSql + "" + strKey + " <![CDATA[ >= ]]> '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("elt".equals(strType)){//小于等于
                        searchSql = searchSql + "" + strKey + " <![CDATA[ <= ]]> '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("like".equals(strType)){//模糊查询
                        searchSql = searchSql + "" + strKey + " CONCAT('%', '" + jsonObject.get("value") + "','%') " + " AND ";
                        continue;
                    }
                }
            }
            System.out.println("第一次拼接"+searchSql);
        }

        //拼接关联条件
        String expandCondition = searchConditionsModel.getExpandCondition();
        if(!expandCondition.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(expandCondition);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strKey = jsonObject.get("key")+"";//查询Key
                    if("relation".equals(strKey)){
                        JSONArray keyValueArray =JSONArray.parseArray(jsonObject.get("value")+"");
                        for(int j=0;j<keyValueArray.size();j++){
                            JSONObject keyValueObject = keyValueArray.getJSONObject(j);
                            relationSql = relationSql + keyValueObject.get("operator")+" join " +keyValueObject.get("tablename")+" on ";
                            JSONArray valueArray =JSONArray.parseArray(keyValueObject.get("value")+"");
                            for(int h=0;h<valueArray.size();h++){
                                JSONObject valueObject = valueArray.getJSONObject(h);
                                String fronttable = valueObject.get("fronttable")+"";
                                String frontfield = valueObject.get("frontfield")+"";
                                String operator = valueObject.get("operator")+"";
                                String backtable = valueObject.get("backtable")+"";
                                String backfield = valueObject.get("backfield")+"";
                                if("eq".equals(operator)){//等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("neq".equals(operator)){//不等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("gt".equals(operator)){//大于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("lt".equals(operator)){//小于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("egt".equals(operator)){//大于等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("elt".equals(operator)){//小于等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                            }
                        }
                        continue;
                    }
                    if("relationvalue".equals(strKey)){
                        JSONArray keyValueArray =JSONArray.parseArray(jsonObject.get("value")+"");
                        for(int j=0;j<keyValueArray.size();j++){
                            JSONObject keyValueObject = keyValueArray.getJSONObject(j);
                            String tablename = keyValueObject.get("tablename")+"";
                            String field = keyValueObject.get("field")+"";
                            String operator = keyValueObject.get("operator")+"";
                            String value = keyValueObject.get("value")+"";

                            if("eq".equals(operator)){//等于
                                if(jsonObject.get("value")==null){
                                    searchSql = searchSql + "" + tablename + " is null" + " AND ";
                                }else{
                                    searchSql = searchSql + "" + tablename +"." + field + " = '" + value + "' AND ";
                                }
                                continue;
                            }
                            if("neq".equals(operator)){//不等于
                                searchSql = searchSql + "" + tablename +"." + field + " != '" + value + "' AND ";
                                continue;
                            }
                            if("notNull".equals(operator)){//不为空
                                searchSql = searchSql + "" + tablename +"." + field + " is not null " + " AND ";
                                continue;
                            }
                            if("gt".equals(operator)){//大于
                                searchSql = searchSql + "" + tablename +"." + field + " <![CDATA[ > ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("lt".equals(operator)){//小于
                                searchSql = searchSql + "" + tablename +"." + field + " <![CDATA[ < ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("egt".equals(operator)){//大于等于
                                searchSql = searchSql + "" + tablename +"." + field + " <![CDATA[ >= ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("elt".equals(operator)){//小于等于
                                searchSql = searchSql + "" + tablename +"." + field + " <![CDATA[ <= ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("like".equals(operator)){//模糊查询
                                searchSql = searchSql + "" + tablename +"." + field + " CONCAT('%', '" + value + "','%') " + " AND ";
                                continue;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        System.out.println("relationSql ===="+relationSql);
        //多表关联 join on等条件拼接完成
        if(!"".equals(relationSql)){
            relationSql = relationSql.substring(0,relationSql.lastIndexOf("AND"));
        }
        if(!"".equals(searchSql)){
            searchSql = " where " + searchSql.substring(0,searchSql.lastIndexOf("AND"));
        }
        System.out.println("拼接的sql是11"+relationSql+searchSql);

        String sql ="";
        if(SQLInterception.sqlValidate(relationSql+searchSql)){//sql注入拦截
            sql = "  "+relationSql+searchSql;
        }
        System.out.println("最后拼接的sql是"+sql);
        return sql;
    }

    public static SqlModel getConditionsPlus(SearchConditionsModel searchConditionsModel){
        PageHelper.startPage(searchConditionsModel.getPageIndex(),searchConditionsModel.getPageSize());
        String searchCondition = searchConditionsModel.getSearchCondition();

        SqlModel sqlModel = new SqlModel();
        String searchSql ="";//查询条件
        String relationSql ="";//关联条件
        String relationSearchSql ="";//关联查询条件
        String orderSql ="";//排序条件

        if(!searchCondition.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(searchCondition);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strKey = jsonObject.get("field")+"";//查询Key
                    String strType = jsonObject.get("operator")+"";//查询类别

                    if("eq".equals(strType)){//等于
                        if(jsonObject.get("value")==null){
                            searchSql = searchSql + ProjectConstant.Table_A + strKey + " is null" + " AND ";
                        }else{
                            searchSql = searchSql + ProjectConstant.Table_A + strKey + " = '" + jsonObject.get("value") + "' AND ";
                        }
                        continue;
                    }
                    if("neq".equals(strType)){//不等于
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " != '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("notNull".equals(strType)){//不为空
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " is not null " + " AND ";
                        continue;
                    }
                    if("gt".equals(strType)){//大于
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " >  '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("lt".equals(strType)){//小于
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " <  '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("egt".equals(strType)){//大于等于
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " >=  '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("elt".equals(strType)){//小于等于
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " <=  '" + jsonObject.get("value") + "' AND ";
                        continue;
                    }
                    if("like".equals(strType)){//模糊查询
                        searchSql = searchSql + ProjectConstant.Table_A + strKey + " LIKE CONCAT('%', '" + jsonObject.get("value") + "','%') " + " AND ";
                        continue;
                    }
                }
            }
            System.out.println("第一次拼接"+searchSql);
        }

        //拼接关联条件
        String expandCondition = searchConditionsModel.getExpandCondition();
        if(!expandCondition.isEmpty()) {
            JSONArray jsonArray = JSONArray.parseArray(expandCondition);
            if(jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String strKey = jsonObject.get("key")+"";//查询Key
                    if("relation".equals(strKey)){
                        JSONArray keyValueArray =JSONArray.parseArray(jsonObject.get("value")+"");
                        for(int j=0;j<keyValueArray.size();j++){
                            JSONObject keyValueObject = keyValueArray.getJSONObject(j);
                            relationSql = relationSql + keyValueObject.get("operator")+" join " +keyValueObject.get("tablename")+" on ";
                            JSONArray valueArray =JSONArray.parseArray(keyValueObject.get("value")+"");
                            for(int h=0;h<valueArray.size();h++){
                                JSONObject valueObject = valueArray.getJSONObject(h);
                                String fronttable = valueObject.get("fronttable")+"";
                                String frontfield = valueObject.get("frontfield")+"";
                                String operator = valueObject.get("operator")+"";
                                String backtable = valueObject.get("backtable")+"";
                                String backfield = valueObject.get("backfield")+"";
                                if("eq".equals(operator)){//等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("neq".equals(operator)){//不等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("gt".equals(operator)){//大于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("lt".equals(operator)){//小于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("egt".equals(operator)){//大于等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                                if("elt".equals(operator)){//小于等于
                                    relationSql = relationSql + fronttable+"." + frontfield + " = " + backtable + "."+backfield + "  AND ";
                                    continue;
                                }
                            }
                        }
                        continue;
                    }
                    if("relationvalue".equals(strKey)){
                        JSONArray keyValueArray =JSONArray.parseArray(jsonObject.get("value")+"");
                        for(int j=0;j<keyValueArray.size();j++){
                            JSONObject keyValueObject = keyValueArray.getJSONObject(j);
                            String tablename = keyValueObject.get("tablename")+"";
                            String field = keyValueObject.get("field")+"";
                            String operator = keyValueObject.get("operator")+"";
                            String value = keyValueObject.get("value")+"";

                            if("eq".equals(operator)){//等于
                                if(jsonObject.get("value")==null){
                                    relationSearchSql = relationSearchSql + "" + tablename + " is null" + " AND ";
                                }else{
                                    relationSearchSql = relationSearchSql + "" + tablename +"." + field + " = '" + value + "' AND ";
                                }
                                continue;
                            }
                            if("neq".equals(operator)){//不等于
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " != '" + value + "' AND ";
                                continue;
                            }
                            if("notNull".equals(operator)){//不为空
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " is not null " + " AND ";
                                continue;
                            }
                            if("gt".equals(operator)){//大于
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " <![CDATA[ > ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("lt".equals(operator)){//小于
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " <![CDATA[ < ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("egt".equals(operator)){//大于等于
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " <![CDATA[ >= ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("elt".equals(operator)){//小于等于
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " <![CDATA[ <= ]]> '" + value + "' AND ";
                                continue;
                            }
                            if("like".equals(operator)){//模糊查询
                                relationSearchSql = relationSearchSql + "" + tablename +"." + field + " CONCAT('%', '" + value + "','%') " + " AND ";
                                continue;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        System.out.println("relationSql ===="+relationSql);
        //多表关联 join on等条件拼接完成
        if(!"".equals(relationSql)){
            relationSql = relationSql.substring(0,relationSql.lastIndexOf("AND"));
        }
        System.out.println("拼接的sql是11"+relationSql+searchSql);


        //排序条件
        if(!searchConditionsModel.getOrderCondition().isEmpty()) {
            String str = searchConditionsModel.getOrderCondition();
            String []strArray = str.split(",");
            String rowIds="";
            if(strArray.length>0){
                for(int i = 0;i<strArray.length;i++){
                    if(rowIds!=""){
                        rowIds=rowIds + ","+ ProjectConstant.Table_A + strArray[i];
                    }else{
                        rowIds = rowIds + ProjectConstant.Table_A + strArray[i];
                    }
                }
                orderSql = rowIds;
            }
        }
        if(!"".equals(orderSql)){
            orderSql = orderSql + ",";
        }
        System.out.println("排序条件为==="+orderSql);

        //sql注入拦截
        /*if(SQLInterception.sqlValidate(searchSql)){
            sqlModel.setSearchSql(searchSql);
        }*/
        sqlModel.setSearchSql(searchSql);//先放开sql注入拦截
        if(SQLInterception.sqlValidate(orderSql)){
            sqlModel.setOrderSql(orderSql);
        }
        return sqlModel;
    }

    /**
     * @Description: 替换特殊查询条件，支持一个
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/12/1 23:24
     * @UpdateRemark:   修改内容
     * @date 2018/12/1 23:24
     */
    public static String replaceKey(String key,String keyValue,String searchSql){
        String sql = "";
        if(!"".equals(searchSql)){
            String []str = searchSql.trim().split("AND");
            for(int i = 0; i<str.length;i++){
                String s = str[i].substring(0,str[i].indexOf("=")).trim();
                System.out.println(s);
                if(s.equals(ProjectConstant.Table_A+key)){
                    str[i] = str[i].replaceAll(ProjectConstant.Table_A+key,keyValue);
                }
                sql = sql +str[i] +" AND ";
            }
        }
        System.out.println("成败在此一举======"+sql);
        return sql;
    }

    /**
     * @Description: 替换特殊查询条件，支持多个
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/12/1 23:24
     * @UpdateRemark:   修改内容
     * @date 2018/12/1 23:24
     */
    public static String replaceKey(Map<String,String> map, String searchSql){
        String sql = "";
        if(map.size()>0){
            if(!"".equals(searchSql)){
                for(Map.Entry<String, String> vo : map.entrySet()){
                    String []str = searchSql.trim().split("AND");
                    for(int i = 0; i<str.length;i++){
                        String s = str[i].substring(0,str[i].indexOf("=")).trim();
                        System.out.println(s);
                        if(s.equals(ProjectConstant.Table_A+vo.getKey())){
                            str[i] = str[i].replaceAll(ProjectConstant.Table_A+vo.getKey(),vo.getValue());
                            sql = sql +str[i] +" AND ";
                            continue;
                        }
                        //System.out.println("执行内循环"+i+"次，sql值为："+sql);
                    }
                }
            }
        }
        System.out.println("成败在此一举======"+sql);
        return sql;
    }

    /**
     * @Description: 将查询条件实体类转换成导出条件实体类
     * @param
     * @return
     * @throws
     * @author wukunfan
     * @UpdateUser:
     * @UpdateDate:     2018/12/2 16:38
     * @UpdateRemark:   修改内容
     * @date 2018/12/2 16:38
     */
    public static SearchConditionsModel searchToConditions(ConditionsModel conditionsModel){
        SearchConditionsModel searchConditionsModel = new SearchConditionsModel();
        if(!"".equals(conditionsModel.getSearchCondition())){
            searchConditionsModel.setSearchCondition(conditionsModel.getSearchCondition());
        }
        if(!"".equals(conditionsModel.getOrderCondition())){
            searchConditionsModel.setSearchCondition(conditionsModel.getOrderCondition());
        }
        if(conditionsModel.getExpandModel()!=null){
            searchConditionsModel.setExpandModel(conditionsModel.getExpandModel());
        }
        searchConditionsModel.setPageIndex(conditionsModel.getPageIndex());
        searchConditionsModel.setPageSize(conditionsModel.getPageSize());
        return searchConditionsModel;
    }
}
