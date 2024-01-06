package com.franc.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan(value="com.franc.dao.maria2", sqlSessionFactoryRef="maria2SessionFactory")
@EnableTransactionManagement
public class Maria2DataBaseConfig {

    @Bean(name="maria2DataSource")
    @ConfigurationProperties(prefix="spring.datasource.maria2")
    public DataSource maria2DataSource() {
        //application.properties에서 정의한 DB 연결 정보를 빌드
        return DataSourceBuilder.create().build();
    }

    @Bean(name="maria2SessionFactory")
    public SqlSessionFactory maria2SessionFactory(@Qualifier("maria2DataSource") DataSource maria2DataSource, ApplicationContext applicationContext) throws Exception{
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(maria2DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/maria2/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="maria2SessionTemplate")
    public SqlSessionTemplate maria2SessionTemplate(SqlSessionFactory maria2SessionFactory) throws Exception{
        return new SqlSessionTemplate(maria2SessionFactory);
    }
}
