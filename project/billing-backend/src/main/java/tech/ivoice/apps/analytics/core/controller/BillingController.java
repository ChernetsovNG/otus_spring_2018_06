package tech.ivoice.apps.analytics.core.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tech.ivoice.apps.analytics.core.service.BillingService;
import tech.ivoice.common.billing.BillingResource;
import tech.ivoice.common.billing.dto.CampaignList;

import java.util.Map;

@RestController
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/billing/spent-resources")
    public ResponseEntity<Mono<Map<BillingResource, Double>>> getResourcesByCampaignsAndTime(
        @RequestParam(value = "appUuid") String appUuid, @RequestParam(value = "timestampStart", required = false) Long timestampStart,
        @RequestParam(value = "timestampFinish", required = false) Long timestampFinish, @RequestBody CampaignList campaigns) {

        Mono<Map<BillingResource, Double>> overallSpentResources;
        if (timestampStart == null && timestampFinish == null) {
            overallSpentResources = billingService
                .getOverallSpentResourcesByCampaigns(appUuid, campaigns);
        } else if (timestampStart != null && timestampFinish != null && timestampFinish > timestampStart) {
            overallSpentResources = billingService
                .getOverallSpentResourcesByCampaignsAndTime(appUuid, campaigns, timestampStart, timestampFinish);
        } else {
            throw new IllegalArgumentException("Timestamp params should be null or not null both at the same time. timestampFinish should be > timestampStart");
        }
        return ResponseEntity.ok(overallSpentResources);
    }
}
