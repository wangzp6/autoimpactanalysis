package com.example.autoimpactanalysis.config.jdbc;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: MysqlConfig
 * @Author: kevin
 * @Date: 2022/6/20 10:47
 * @Description: TODO
 * @Version: V1.0
 */
@Configuration
@MapperScan(basePackages = "com.example.autoimpactanalysis.mapper.mysql.*",sqlSessionFactoryRef = "MysqlSqlSessionFactory")
public class MysqlConfig {
    @Primary
    @Bean(name = "DataSource_mysql")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "MysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("DataSource_mysql") DataSource mysqldataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(mysqldataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/mysql/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "MysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("DataSource_mysql") DataSource mysqldataSource) {
        return new DataSourceTransactionManager(mysqldataSource);
    }

    @Primary
    @Bean(name = "MysqlSqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("MysqlSqlSessionFactory") SqlSessionFactory mysqlsqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(mysqlsqlSessionFactory);
    }
}
