package tech.ivoice.apps.analytics.core.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tech.ivoice.apps.analytics.core.model.BillingApp;

@Repository
public interface BillingAppsRepository extends ReactiveMongoRepository<BillingApp, String> {

    Mono<BillingApp> findByAppUuid(String appUuid);
}
