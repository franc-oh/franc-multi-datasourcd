package com.franc.config.database;

import com.zaxxer.hikari.HikariDataSource;
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
@MapperScan(value="com.franc.dao.maria1", sqlSessionFactoryRef="maria1SessionFactory")
@EnableTransactionManagement
public class Maria1DataBaseConfig {

    @Bean(name="maria1DataSource")
    @ConfigurationProperties(prefix="spring.datasource.maria1")
    public DataSource maria1DataSource() {
        //application.properties에서 정의한 DB 연결 정보를 빌드
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name="maria1SessionFactory")
    public SqlSessionFactory maria1SessionFactory(@Qualifier("maria1DataSource") DataSource maria1DataSource, ApplicationContext applicationContext) throws Exception{
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(maria1DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/maria1/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="maria1SessionTemplate")
    public SqlSessionTemplate maria1SessionTemplate(SqlSessionFactory maria1SessionFactory) throws Exception{
        return new SqlSessionTemplate(maria1SessionFactory);
    }
}
