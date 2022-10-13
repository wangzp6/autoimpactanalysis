package com.example.autoimpactanalysis;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: AutoimpactanalysisApplication
 * @Author: kevin
 * @Date: 2022/6/13 15:31
 * @Description: 启动类
 * EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})意思是去掉springboot服务启动时对于数据源配置的自动导入。
 * 后面我们会手动配置多个数据源。
 * 如果有需要，也可把事务管理与mybatis的自动导入去掉
 * @SpringBootApplication注解中已经包含@EnableAutoConfiguration注解，不需要重复定义
 * @Version: V1.0
 */
@Slf4j
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, MybatisAutoConfiguration.class})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@MapperScan(basePackages = {"com.example.autoimpactanalysis.mapper"})
public class AutoimpactanalysisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoimpactanalysisApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        log.info("进入AutoimpactanalysisApplication方法");
        return "I am alive;";
    }
}
