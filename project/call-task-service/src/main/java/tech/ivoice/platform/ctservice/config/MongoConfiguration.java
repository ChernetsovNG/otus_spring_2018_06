package tech.ivoice.platform.ctservice.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"tech.ivoice.common.repository"})
@EnableAutoConfiguration
public class MongoConfiguration {
}
