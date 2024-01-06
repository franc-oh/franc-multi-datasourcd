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
@MapperScan(value="com.franc.dao.postgres", sqlSessionFactoryRef="postgresSessionFactory")
@EnableTransactionManagement
public class PostgresDataBaseConfig {

    @Primary
    @Bean(name="postgresDataSource")
    @ConfigurationProperties(prefix="spring.datasource.postgres")
    public DataSource postgresDataSource() {
        //application.properties에서 정의한 DB 연결 정보를 빌드
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name="postgresSessionFactory")
    public SqlSessionFactory postgresSessionFactory(@Qualifier("postgresDataSource") DataSource postgresDataSource, ApplicationContext applicationContext) throws Exception{
        //세션 생성 시, 빌드된 DataSource를 세팅하고 SQL문을 관리할 mapper.xml의 경로를 알려준다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(postgresDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/postgres/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name="postgresSessionTemplate")
    public SqlSessionTemplate postgresSessionTemplate(SqlSessionFactory postgresSessionFactory) throws Exception{
        return new SqlSessionTemplate(postgresSessionFactory);
    }
}
