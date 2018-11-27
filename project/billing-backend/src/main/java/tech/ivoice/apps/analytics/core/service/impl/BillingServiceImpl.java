package tech.ivoice.apps.analytics.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.ivoice.apps.analytics.core.model.BillingApp;
import tech.ivoice.apps.analytics.core.repository.BillingAppsRepository;
import tech.ivoice.apps.analytics.core.service.BillingService;
import tech.ivoice.common.billing.BillingResource;
import tech.ivoice.common.billing.dto.CampaignList;
import tech.ivoice.common.billing.dto.CampaignSpentResources;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BillingServiceImpl implements BillingService {

    private static final String GET_RESOURCES_PATH = "/billing/spent-resources";

    private final BillingAppsRepository billingAppsRepository;

    private final WebClient webClient;

    public BillingServiceImpl(BillingAppsRepository billingAppsRepository, WebClient webClient) {
        this.billingAppsRepository = billingAppsRepository;
        this.webClient = webClient;
    }

    @Override
    public Mono<Map<BillingResource, Double>> getOverallSpentResourcesByCampaigns(String appUuid, CampaignList campaigns) {
        Mono<BillingApp> billingAppMono = getBillingApp(appUuid);
        return billingAppMono.flatMap(billingApp -> {
            String spentResourcesUrl = billingApp.getSpentResourcesUrl();
            Flux<CampaignSpentResources> campaignsSpentResources =
                    getCampaignsSpentResources(spentResourcesUrl, campaigns);
            return calculateOverallResources(campaignsSpentResources);
        }).doOnError(e -> log.error(e.getMessage()));
    }

    @Override
    public Mono<Map<BillingResource, Double>> getOverallSpentResourcesByCampaignsAndTime(
            String appUuid, CampaignList campaigns, Long timestampStart, Long timestampFinish) {
        Mono<BillingApp> billingAppMono = getBillingApp(appUuid);
        return billingAppMono.flatMap(billingApp -> {
            String spentResourcesUrl = billingApp.getSpentResourcesUrl();
            Flux<CampaignSpentResources> campaignsSpentResources =
                    getCampaignsSpentResources(spentResourcesUrl, campaigns, timestampStart, timestampFinish);
            return calculateOverallResources(campaignsSpentResources);
        }).doOnError(e -> log.error(e.getMessage()));
    }

    private Mono<BillingApp> getBillingApp(String appUuid) {
        Mono<BillingApp> notFoundAppException = Mono.error(
                new EntityNotFoundException("In database no app with uuid: " + appUuid));
        return billingAppsRepository
                .findByAppUuid(appUuid)
                .switchIfEmpty(notFoundAppException)
                .doOnError(e -> log.error(e.getMessage()));
    }

    private Flux<CampaignSpentResources> getCampaignsSpentResources(String spentResourcesUrl, CampaignList campaigns) {
        return webClient
                .post()
                .uri(spentResourcesUrl + GET_RESOURCES_PATH)
                .body(BodyInserters.fromObject(campaigns))
                .retrieve()
                .bodyToFlux(CampaignSpentResources.class)
                .doOnError(e -> log.error(e.getMessage()));
    }

    private Flux<CampaignSpentResources> getCampaignsSpentResources(String spentResourcesUrl, CampaignList campaigns,
                                                                    Long timestampStart, Long timestampFinish) {
        URI uri = URI.create(spentResourcesUrl);
        return webClient
                .post()
                .uri(builder -> builder
                        .scheme(uri.getScheme()).host(uri.getHost()).port(uri.getPort()).path(GET_RESOURCES_PATH)
                        .queryParam("timestampStart", timestampStart)
                        .queryParam("timestampFinish", timestampFinish)
                        .build())
                .body(BodyInserters.fromObject(campaigns))
                .retrieve()
                .bodyToFlux(CampaignSpentResources.class)
                .doOnError(e -> log.error(e.getMessage()));
    }

    private Mono<Map<BillingResource, Double>> calculateOverallResources(Flux<CampaignSpentResources> campaignsSpentResources) {
        Map<BillingResource, Double> overallSpentResources = new HashMap<>();
        return campaignsSpentResources
                .map(CampaignSpentResources::getSpentResources)
                .reduce(overallSpentResources, (accumulator, currentCampaign) -> {
                    currentCampaign.forEach((billingResource, value) -> {
                        accumulator.putIfAbsent(billingResource, 0.0);
                        accumulator.put(billingResource, accumulator.get(billingResource) + value);
                    });
                    return accumulator;
                });
    }
}
