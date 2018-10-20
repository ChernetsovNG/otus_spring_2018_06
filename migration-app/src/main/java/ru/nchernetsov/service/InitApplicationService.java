package ru.nchernetsov.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.nchernetsov.repository.mongodb.MongoDBAuthorRepository;

@Service
public class InitApplicationService {

    private final MongoDBAuthorRepository mongoDBAuthorRepository;

    public InitApplicationService(MongoDBAuthorRepository mongoDBAuthorRepository) {
        this.mongoDBAuthorRepository = mongoDBAuthorRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void clearDatabases() {
        mongoDBAuthorRepository.deleteAll();
    }

}
