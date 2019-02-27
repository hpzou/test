package com.anshare.project.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 描述：
 * <p>
 * Author: wukunfan
 * Date: 2018/10/29 15:32
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig{
    // 设置默认TOKEN，方便测试
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiznrqHnkIblkZgsMDAwMDAwMDAtMTAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAwLDAwMDAwMDAwLTAwMDAtMDAwMS0wMDAwLTAwMDAwMDAwMDAwMCIsImV4cCI6MTU0MDQ1MTc0MX0.222orbs5C3ayN7iknj4QMAGIUz5PHEezlv0DqJpGpQe7Ec1E3BtOYZlWr9tmHmBQHaPLxkxfY5vpe1nFW3jpaA";

    /*@Bean
    public Docket api() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("auth").description("令牌").defaultValue(TOKEN).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.anshare.project.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error")).build(), new ResponseMessageBuilder().code(403).message("Forbidden!!!!!").build()));
        return docket;
    }*/

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.anshare.project.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("苏州廉情系统")
                .description("苏州廉情系统")
                .version("1.0")
                .build();
    }
}
