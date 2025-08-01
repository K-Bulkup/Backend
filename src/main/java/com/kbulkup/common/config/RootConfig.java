package com.kbulkup.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySources({@PropertySource("classpath:/config/application-dev.properties"),
        @PropertySource("classpath:/application.properties")})
// 1. 일반 컴포넌트를 스캔할 때는 Mapper 인터페이스를 제외시킵니다.
@ComponentScan(
        basePackages = "com.kbulkup",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Mapper.class)
)
// 2. Mapper 인터페이스는 @MapperScan으로만 스캔합니다.
@MapperScan(
        basePackages = "com.kbulkup",
        annotationClass = Mapper.class
)
@EnableTransactionManagement
public class RootConfig {

    @Value("${db.driverClassName}")
    private String driverClassName;
    @Value("${db.url}")
    private String url;
    @Value("${db.username}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public DataSource dataSource() {
        // HikariCP 설정 객체 생성
        HikariConfig config = new HikariConfig();

        // 데이터베이스 연결 정보 설정
        config.setDriverClassName(driverClassName);          // JDBC 드라이버 클래스
        config.setJdbcUrl(url);                    // 데이터베이스 URL
        config.setUsername(username);              // 사용자명
        config.setPassword(password);              // 비밀번호

        // 커넥션 풀 추가 설정 (선택사항)
        config.setMaximumPoolSize(10);             // 최대 커넥션 수
        config.setMinimumIdle(5);                  // 최소 유지 커넥션 수
        config.setConnectionTimeout(30000);       // 연결 타임아웃 (30초)
        config.setIdleTimeout(600000);            // 유휴 타임아웃 (10분)

        // HikariDataSource 생성 및 반환
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());
        sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*.xml"));
        return sqlSessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource());
        return manager;
    }
}
