package ru.nchernetsov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.*;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.integration.router.MethodInvokingRouter;
import org.springframework.messaging.MessageHandler;
import ru.nchernetsov.domain.Book;
import ru.nchernetsov.domain.Order;
import ru.nchernetsov.integration.BookToOrderConverter;
import ru.nchernetsov.integration.OrderProcessor;

import java.util.concurrent.TimeUnit;

@Configuration
@IntegrationComponentScan
public class InfrastructureConfiguration {

    private static final int READ_FROM_MONGO_DELTA_TAU_MILLIS = 1000;

    @MessagingGateway
    public interface BookService {
        @Gateway(requestChannel = "sendBook.input")
        void book(Book book);
    }

    @Bean
    @Autowired
    public IntegrationFlow sendBook(MongoDbFactory mongo) {
        return f -> f
            .log()
            .transform(Transformers.converter(bookToOrderConverter()))
            .log()
            .handle(mongoOutboundAdapter(mongo));
    }

    @Bean
    @Autowired
    public MessageHandler mongoOutboundAdapter(MongoDbFactory mongo) {
        MongoDbStoringMessageHandler mongoHandler = new MongoDbStoringMessageHandler(mongo);
        mongoHandler.setCollectionNameExpression(new LiteralExpression("order"));
        return mongoHandler;
    }

    @Bean
    public BookToOrderConverter bookToOrderConverter() {
        return new BookToOrderConverter();
    }

    @Bean
    @Autowired
    public IntegrationFlow processProduct(MongoDbFactory mongo) {
        return IntegrationFlows.from(mongoMessageSource(mongo),
            c -> c.poller(Pollers.fixedDelay(READ_FROM_MONGO_DELTA_TAU_MILLIS, TimeUnit.MILLISECONDS)))
            .route(Order::isPremium, this::routeOrders)
            .handle(mongoOutboundAdapter(mongo))
            .get();
    }

    @Bean
    @Autowired
    public MessageSource<Object> mongoMessageSource(MongoDbFactory mongo) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongo, new LiteralExpression("{'processed' : false}"));
        messageSource.setExpectSingleResult(true);
        messageSource.setEntityClass(Order.class);
        messageSource.setCollectionNameExpression(new LiteralExpression("order"));
        return messageSource;
    }

    private RouterSpec<Boolean, MethodInvokingRouter> routeOrders(RouterSpec<Boolean, MethodInvokingRouter> mapping) {
        return mapping
            .subFlowMapping(true, sf -> sf.handle(orderProcessor(), "fastProcess"))
            .subFlowMapping(false, sf -> sf.handle(orderProcessor(), "process"));
    }

    @Bean
    public OrderProcessor orderProcessor() {
        return new OrderProcessor();
    }

}
