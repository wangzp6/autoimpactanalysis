package com.example.autoimpactanalysis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: SwaggerConfig
 * @Author: kevin
 * @Date: 2022/6/14 09:48
 * @Description: @EnableSwagger2   //Swagger2注解
 * @Description: @EnableOpenApi   //Swagger3注解
 * @Version: V1.0
 */

//@EnableSwagger2   //Swagger2注解
@Configuration
@EnableOpenApi   //Swagger3注解
public class SwaggerConfig {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     * @return
     */
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("标准接口")
                .apiInfo(apiInfo("Spring Boot中使用Swagger3构建RESTful APIs", "1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.autoimpactanalysis.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://ip:port/swagger-ui.html
     * http://localhost:9080/swagger-ui.html   swagger2访问页面
     * http://localhost:9080/swagger-ui/index.html   swagger3访问页面
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("这是swagger测试页面")
                .termsOfServiceUrl("http://localhost:9080/autoimpactanalysis/swagger-ui/index.html")
                .version(version)
                .build();
    }
}
