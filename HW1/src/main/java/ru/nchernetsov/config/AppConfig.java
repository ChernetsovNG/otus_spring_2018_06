package ru.nchernetsov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.nchernetsov.dao.StudentDao;
import ru.nchernetsov.dao.StudentDaoMock;

@Configuration
public class AppConfig {

    @Bean
    public StudentDao studentDao() {
        return new StudentDaoMock();
    }

    // для доступа к property-файлам
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
