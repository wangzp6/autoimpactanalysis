package com.example.autoimpactanalysis.config.jdbc;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
/**
 * @project_name: autoimpactanalysis
 * @ClassName: AdsConfig
 * @Author: kevin
 * @Date: 2022/6/20 10:58
 * @Description: TODO
 * @Version: V1.0
 */
public class AdsConfig {
    @Bean(name = "DataSource_ads")
    @ConfigurationProperties(prefix = "spring.datasource.oracle-ads")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "AdsSqlSessionFactory")
    public SqlSessionFactory adsSqlSessionFactory(@Qualifier("DataSource_ads") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/autoimpactanalysis/ads/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "AdsTransactionManager")
    public DataSourceTransactionManager adsTransactionManager(@Qualifier("DataSource_ads") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "AdsSqlSessionTemplate")
    public SqlSessionTemplate adsSqlSessionTemplate(@Qualifier("AdsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
