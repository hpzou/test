package com.anshare.project;

import com.anshare.project.core.Util.FileUtil;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize("50MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }

    /**
     （1）在启动类App.java类中继承：WebMvcConfigurerAdapter
     （2）覆盖方法：configureContentNegotiation
     favorPathExtension表示支持后缀匹配，
     属性ignoreAcceptHeader默认为fasle，表示accept-header匹配，defaultContentType开启默认匹配。
     例如：请求aaa.xx，若设置<entry key="xx" value="application/xml"/> 也能匹配以xml返回。
     根据以上条件进行一一匹配最终，得到相关并符合的策略初始化ContentNegotiationManager
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }



}

