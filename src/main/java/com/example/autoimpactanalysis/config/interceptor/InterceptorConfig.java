package com.example.autoimpactanalysis.config.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @project_name: autoimpactanalysis
 * @ClassName: InterceptorConfig
 * @Author: kevin
 * @Date: 2022/6/14 09:48
 * @Description: TODO
 * @Version: V1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断token是否有合法 决定是否需要登录
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/register/","/**/userImport","/**/userExport","/files/**","/svn/**","/**/swagger-resources/**","/**/swagger-ui/**","/**/v2/**","/**/error","/source/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
