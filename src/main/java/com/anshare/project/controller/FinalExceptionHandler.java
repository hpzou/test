package com.anshare.project.controller;

import com.alibaba.fastjson.JSON;
import com.anshare.project.core.ProjectConstant;
import com.anshare.project.core.ResultCore.ResultGenerator;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @ClassName FinalExceptionHandler
* @Description 针对非controller层的异常统一处理
* @Author wukunfan
* @Date 2018/11/12 18:24
* @UpdateUser:
* @UpdateDate:     2018/11/12 18:24
* @UpdateRemark:   修改内容
* @Version 1.0
**/

@Controller
public class FinalExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request,HttpServletResponse response) throws  IOException{
        responseResult(request,response);
    }

    private void responseResult(HttpServletRequest request,HttpServletResponse response) throws  IOException{
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        System.out.println("关键信息+++++++++++++++++++++++"+statusCode);
        response.setStatus(200);
        if(statusCode == 404){
            response.getWriter().write(JSON.toJSONString(ResultGenerator.genFailCodeResult(ProjectConstant.URL_INVALID,"客户端网络异常")));
        }else if(statusCode == 403){
            response.getWriter().write(JSON.toJSONString(ResultGenerator.genFailCodeResult(ProjectConstant.TOKEN_INVALID,"Token失效")));
        }else{
            response.getWriter().write(JSON.toJSONString(ResultGenerator.genFailCodeResult(ProjectConstant.TOKEN_INVALID,"Token失效")));
        }
    }
}
