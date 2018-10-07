package ru.nchernetsov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.Message;
import ru.nchernetsov.integration.PrintService;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final PrintService service;

    public IntegrationConfig(PrintService service) {
        this.service = service;
    }

    @Bean
    public DirectChannel inputChannelDSL() {
        return MessageChannels.direct("inputChannelDSL").get();
    }

    @Bean
    public DirectChannel outputChannelDSL() {
        return MessageChannels.direct("outputChannelDSL").get();
    }

    @Bean
    public IntegrationFlow serviceActivatorFlow() {
        return IntegrationFlows.from(inputChannelDSL())
            .handle(message -> service.print((Message<String>) message))
            .get();
    }

    @Bean
    public MessagingTemplate messagingTemplate() {
        return new MessagingTemplate();
    }

}
