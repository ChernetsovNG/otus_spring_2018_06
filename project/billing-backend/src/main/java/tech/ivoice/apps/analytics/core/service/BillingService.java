package tech.ivoice.apps.analytics.core.service;

import reactor.core.publisher.Mono;
import tech.ivoice.common.billing.BillingResource;
import tech.ivoice.common.billing.dto.CampaignList;

import java.util.Map;

public interface BillingService {

    /**
     * Получить общие затраченные ресурсы по списку кампаний
     *
     * @param appUuid   id приложения
     * @param campaigns список id кампаний
     * @return общие затраченные ресурсы
     */
    Mono<Map<BillingResource, Double>> getOverallSpentResourcesByCampaigns(String appUuid, CampaignList campaigns);

    /**
     * Получить общие затраченные ресурсы по списку кампаний и в заданном временном интервале
     *
     * @param appUuid         id приложения
     * @param campaigns       список id кампаний
     * @param timestampStart  начальное время secondsSinceEpoch в зоне UTC-0
     * @param timestampFinish конечное время secondsSinceEpoch в зоне UTC-0
     * @return общие затраченные ресурсы
     */
    Mono<Map<BillingResource, Double>> getOverallSpentResourcesByCampaignsAndTime(String appUuid, CampaignList campaigns, Long timestampStart, Long timestampFinish);
}
