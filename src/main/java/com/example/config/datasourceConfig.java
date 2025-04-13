package com.example.config;

import org.hibernate.dialect.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.example.dialect.SQLiteDialect;

import javax.sql.DataSource;

@Configuration
public class datasourceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:databases/Biblioteca.db");
        return dataSource;
    }

    @Bean
    public Dialect jdbcDialect() {
        return SQLiteDialect.INSTANCE;
    }

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions();
    }
}
