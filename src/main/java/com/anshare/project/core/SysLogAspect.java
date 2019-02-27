package com.anshare.project.core;


import com.anshare.project.core.Util.DateUtil;
import com.anshare.project.core.Util.RedisUtil;
import com.anshare.project.model.Log;
import com.anshare.project.service.LogService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
/**
* @ClassName SysLogAspect
* @Description 系统日志：切面处理实体类
* @Author wukunfan
* @Date 2018/12/4 22:42
* @UpdateUser:  
* @UpdateDate:     2018/12/4 22:42
* @UpdateRemark:   修改内容
* @Version 1.0
**/
public class SysLogAspect implements Ordered {
    @Autowired
    private LogService logService;

    @Autowired
    RedisUtil redis;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.anshare.project.core.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @AfterReturning(pointcut ="logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        Log sysLog = new Log();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setRegion(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setAccount(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = StringUtils.join(args, ",");
        sysLog.setContent(params);
        sysLog.setOpttype("0");//失败

        sysLog.setOpttime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        //获取用户名
        sysLog.setUserid(redis.getStr("currentUser"));
        //调用service保存SysLog实体类到数据库
        logService.save(sysLog,true);
    }


    @AfterThrowing(pointcut = "logPoinCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint,Throwable e){
        //保存日志
        Log sysLog = new Log();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setRegion(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setAccount(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = StringUtils.join(args, ",");
        sysLog.setContent(params);
        sysLog.setOpttype("1");//失败

        sysLog.setOpttime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        //获取用户名
        sysLog.setUserid(redis.getStr("currentUser"));
        sysLog.setResultcontent(e.getMessage());
        //调用service保存SysLog实体类到数据库
        logService.save(sysLog,true);
    }

    //这样就实现了我们自己写的aop在事务介入之前就执行了,数值越小优先级越高
    @Override
    public int getOrder() {
        return 1;
    }
}
