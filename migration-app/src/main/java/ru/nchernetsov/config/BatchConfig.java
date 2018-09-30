package ru.nchernetsov.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @Autowired
    public MongoOperations mongoOperations;

    @Bean
    public Step sqlToMongoMigrationAuthorsStep() {
        return stepBuilderFactory.get("sqlToMongoMigrationStep")
            .<ru.nchernetsov.domain.sql.Author, ru.nchernetsov.domain.mongodb.Author>chunk(5)
            .reader(sqlAuthorReader())
            .processor(sqlToMongoAuthorProcessor())
            .writer(mongoAuthorWriter())
            .build();
    }

    @Bean
    public JpaPagingItemReader<ru.nchernetsov.domain.sql.Author> sqlAuthorReader() {
        JpaPagingItemReader<ru.nchernetsov.domain.sql.Author> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setQueryString("SELECT a FROM Author a");
        jpaPagingItemReader.setPageSize(10);
        return jpaPagingItemReader;
    }

    @Bean
    public ItemProcessor<ru.nchernetsov.domain.sql.Author, ru.nchernetsov.domain.mongodb.Author> sqlToMongoAuthorProcessor() {
        return sqlAuthor -> {
            ru.nchernetsov.domain.mongodb.Author mongoDbAuthor = new ru.nchernetsov.domain.mongodb.Author();
            mongoDbAuthor.setName(sqlAuthor.getName());
            return mongoDbAuthor;
        };
    }

    @Bean
    public MongoItemWriter<ru.nchernetsov.domain.mongodb.Author> mongoAuthorWriter() {
        MongoItemWriter<ru.nchernetsov.domain.mongodb.Author> mongoItemWriter = new MongoItemWriter<>();
        mongoItemWriter.setTemplate(mongoOperations);
        return mongoItemWriter;
    }

    @Bean
    public Job sqlToMongoMigrationJob() {
        return jobBuilderFactory.get("sqlToMongoMigrationJob")
            .start(sqlToMongoMigrationAuthorsStep())
            .build();
    }

}
