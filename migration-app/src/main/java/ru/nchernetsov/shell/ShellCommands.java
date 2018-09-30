package ru.nchernetsov.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import ru.nchernetsov.repository.jpa.SqlAuthorRepository;
import ru.nchernetsov.repository.mongodb.MongoDBAuthorRepository;

import java.util.List;

@ShellComponent
public class ShellCommands {

    private final SqlAuthorRepository sqlAuthorRepository;

    private final MongoDBAuthorRepository mongoDBAuthorRepository;

    private final JobLauncher jobLauncher;

    private final Job sqlToMongoMigrationJob;

    @Autowired
    public ShellCommands(SqlAuthorRepository sqlAuthorRepository, MongoDBAuthorRepository mongoDBAuthorRepository,
                         JobLauncher jobLauncher, Job sqlToMongoMigrationJob) {
        this.sqlAuthorRepository = sqlAuthorRepository;
        this.mongoDBAuthorRepository = mongoDBAuthorRepository;
        this.jobLauncher = jobLauncher;
        this.sqlToMongoMigrationJob = sqlToMongoMigrationJob;
    }

    @Transactional(readOnly = true)
    @ShellMethod("Show Authors in SQL database")
    public List<ru.nchernetsov.domain.sql.Author> showAuthorsSql() {
        return sqlAuthorRepository.findAll();
    }

    @Transactional(readOnly = true)
    @ShellMethod("Show Authors in MongoDB")
    public List<ru.nchernetsov.domain.mongodb.Author> showAuthorsMongo() {
        return mongoDBAuthorRepository.findAll();
    }

    @ShellMethod("Start Migration from Sql to Mongo")
    public void migrationStart() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(sqlToMongoMigrationJob, new JobParameters());
    }

    @ShellMethod("Clear all data from MongoDB")
    public void migrationClear() {
        sqlAuthorRepository.deleteAll();

        mongoDBAuthorRepository.deleteAll();
    }

}
