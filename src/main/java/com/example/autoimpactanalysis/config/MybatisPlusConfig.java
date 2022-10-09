package com.example.autoimpactanalysis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: MybatisPlusConfig
 * @Author: kevin
 * @Date: 2022/6/30 9:24
 * @Description: TODO
 * @Version: V1.0
 */
@Configuration
@MapperScan("com.example.autoimpactanalysis.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mysqlMybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
