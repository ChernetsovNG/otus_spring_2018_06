package ru.nchernetsov.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.nchernetsov.dao.StudentDao;
import ru.nchernetsov.dao.StudentDaoMock;

import static ru.nchernetsov.utils.Constants.FILE_SEPARATOR;

@Configuration
public class AppConfig {

    @Bean
    public StudentDao studentDao() {
        return new StudentDaoMock();
    }

    // для доступа к property-файлам
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("i18n" + FILE_SEPARATOR + "bundle");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
