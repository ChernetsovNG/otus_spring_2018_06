package ru.nchernetsov.dao;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public DataSource dataSource() {
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }

}
