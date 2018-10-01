package ru.nchernetsov.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nchernetsov.domain.mongodb.Book;
import ru.nchernetsov.domain.sql.Author;
import ru.nchernetsov.domain.sql.Comment;
import ru.nchernetsov.domain.sql.Genre;
import ru.nchernetsov.repository.mongodb.MongoDBAuthorRepository;
import ru.nchernetsov.repository.mongodb.MongoDBBookRepository;
import ru.nchernetsov.repository.mongodb.MongoDBCommentRepository;
import ru.nchernetsov.repository.mongodb.MongoDBGenreRepository;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final MongoDBAuthorRepository mongoDBAuthorRepository;

    private final MongoDBGenreRepository mongoDBGenreRepository;

    private final MongoDBBookRepository mongoDBBookRepository;

    private final MongoDBCommentRepository mongoDBCommentRepository;

    @Autowired
    public BatchConfig(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory,
                       EntityManagerFactory entityManagerFactory, MongoDBAuthorRepository mongoDBAuthorRepository,
                       MongoDBGenreRepository mongoDBGenreRepository, MongoDBBookRepository mongoDBBookRepository,
                       MongoDBCommentRepository mongoDBCommentRepository) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.mongoDBAuthorRepository = mongoDBAuthorRepository;
        this.mongoDBGenreRepository = mongoDBGenreRepository;
        this.mongoDBBookRepository = mongoDBBookRepository;
        this.mongoDBCommentRepository = mongoDBCommentRepository;
    }

    // Работа по миграции

    @Bean
    public Job sqlToMongoMigrationJob() {
        return jobBuilderFactory.get("sqlToMongoMigrationJob")
                .start(sqlToMongoMigrationBooksStep())
                .build();
    }

    @Bean
    public Step sqlToMongoMigrationBooksStep() {
        return stepBuilderFactory.get("sqlToMongoMigrationBooksStep")
                .<ru.nchernetsov.domain.sql.Book, ru.nchernetsov.domain.mongodb.Book>chunk(5)
                .reader(sqlBookReader())
                .processor(sqlToMongoBookProcessor())
                .writer(mongoBookWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<ru.nchernetsov.domain.sql.Book> sqlBookReader() {
        JpaPagingItemReader<ru.nchernetsov.domain.sql.Book> jpaPagingItemReader = new JpaPagingItemReader<>();
        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setQueryString("SELECT b FROM Book b");
        jpaPagingItemReader.setPageSize(10);
        return jpaPagingItemReader;
    }

    @Bean
    public ItemProcessor<ru.nchernetsov.domain.sql.Book, ru.nchernetsov.domain.mongodb.Book> sqlToMongoBookProcessor() {
        return sqlBook -> {
            ru.nchernetsov.domain.mongodb.Book mongoDbBook = new ru.nchernetsov.domain.mongodb.Book(null, null);
            mongoDbBook.setTitle(sqlBook.getTitle());

            List<Author> sqlAuthors = sqlBook.getAuthors();
            List<Genre> sqlGenres = sqlBook.getGenres();
            List<Comment> sqlComments = sqlBook.getComments();

            List<ru.nchernetsov.domain.mongodb.Author> mongoDBAuthors = sqlAuthors.stream()
                    .map(sqlAuthor -> new ru.nchernetsov.domain.mongodb.Author(null, sqlAuthor.getName()))
                    .collect(Collectors.toList());

            List<ru.nchernetsov.domain.mongodb.Genre> mongoDBGenres = sqlGenres.stream()
                    .map(sqlGenre -> new ru.nchernetsov.domain.mongodb.Genre(null, sqlGenre.getName()))
                    .collect(Collectors.toList());

            List<ru.nchernetsov.domain.mongodb.Comment> mongoDBComments = sqlComments.stream()
                    .map(sqlComment -> new ru.nchernetsov.domain.mongodb.Comment(null, sqlComment.getComment()))
                    .collect(Collectors.toList());

            mongoDbBook.setAuthors(mongoDBAuthors);
            mongoDbBook.setGenres(mongoDBGenres);
            mongoDbBook.setComments(mongoDBComments);

            return mongoDbBook;
        };
    }

    @Bean
    public ItemWriter<Book> mongoBookWriter() {
        return books -> {
            for (Book book : books) {
                List<ru.nchernetsov.domain.mongodb.Author> authors = saveOrGetAuthorsFromMongoDB(book);
                List<ru.nchernetsov.domain.mongodb.Genre> genres = saveOrGetGenresFromMongoDB(book);
                List<ru.nchernetsov.domain.mongodb.Comment> comments = saveCommentsIntoMongoDB(book);

                // Устанавливаем книге ссылки на авторов, жанры и комментарии
                book.setAuthors(authors);
                book.setGenres(genres);
                book.setComments(comments);

                Book savedBook = mongoDBBookRepository.save(book);

                // Устанавливаем авторам, жанрам и комментариям ссылку на книгу
                authors.forEach(author -> author.addBook(savedBook));
                genres.forEach(genre -> genre.addBook(savedBook));
                comments.forEach(comment -> comment.setBook(savedBook));

                mongoDBAuthorRepository.saveAll(authors);
                mongoDBGenreRepository.saveAll(genres);
                mongoDBCommentRepository.saveAll(comments);
            }
        };
    }

    // Если автор уже был сохранён в MongoDB, то получаем его оттуда, иначе сохраняем
    private List<ru.nchernetsov.domain.mongodb.Author> saveOrGetAuthorsFromMongoDB(Book book) {
        List<ru.nchernetsov.domain.mongodb.Author> authors = book.getAuthors();

        List<ru.nchernetsov.domain.mongodb.Author> mongoAuthors = new ArrayList<>();
        for (ru.nchernetsov.domain.mongodb.Author author : authors) {
            ru.nchernetsov.domain.mongodb.Author savedAuthor;
            if (!mongoDBAuthorRepository.existsByName(author.getName())) {
                savedAuthor = mongoDBAuthorRepository.save(author);
            } else {
                savedAuthor = mongoDBAuthorRepository.findByName(author.getName());
            }
            mongoAuthors.add(savedAuthor);
        }

        return mongoAuthors;
    }

    private List<ru.nchernetsov.domain.mongodb.Genre> saveOrGetGenresFromMongoDB(Book book) {
        List<ru.nchernetsov.domain.mongodb.Genre> genres = book.getGenres();

        List<ru.nchernetsov.domain.mongodb.Genre> mongoGenres = new ArrayList<>();
        for (ru.nchernetsov.domain.mongodb.Genre genre : genres) {
            ru.nchernetsov.domain.mongodb.Genre savedGenre;
            if (!mongoDBGenreRepository.existsByName(genre.getName())) {
                savedGenre = mongoDBGenreRepository.save(genre);
            } else {
                savedGenre = mongoDBGenreRepository.findByName(genre.getName());
            }
            mongoGenres.add(savedGenre);
        }

        return mongoGenres;
    }

    private List<ru.nchernetsov.domain.mongodb.Comment> saveCommentsIntoMongoDB(Book book) {
        List<ru.nchernetsov.domain.mongodb.Comment> comments = book.getComments();

        List<ru.nchernetsov.domain.mongodb.Comment> mongoComments = new ArrayList<>();
        for (ru.nchernetsov.domain.mongodb.Comment comment : comments) {
            ru.nchernetsov.domain.mongodb.Comment savedComment = mongoDBCommentRepository.save(comment);
            mongoComments.add(savedComment);
        }

        return mongoComments;
    }

}
