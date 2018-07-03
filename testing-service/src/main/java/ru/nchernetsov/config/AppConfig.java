package ru.nchernetsov.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("i18n/bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
