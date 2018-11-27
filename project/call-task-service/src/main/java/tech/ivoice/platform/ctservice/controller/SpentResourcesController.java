package tech.ivoice.platform.ctservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import tech.ivoice.common.billing.dto.CampaignList;
import tech.ivoice.common.billing.dto.CampaignSpentResources;
import tech.ivoice.platform.ctservice.service.SpentResourcesService;

@RestController
@Slf4j
public class SpentResourcesController {

    private final SpentResourcesService spentResourcesService;

    public SpentResourcesController(SpentResourcesService spentResourcesService) {
        this.spentResourcesService = spentResourcesService;
    }

    @PostMapping("/billing/spent-resources")
    public Flux<CampaignSpentResources> getSpentResourcesByCampaignsAndTime(@RequestBody CampaignList campaigns,
                                                                            @RequestParam(value = "timestampStart", required = false) Long timestampStart,
                                                                            @RequestParam(value = "timestampFinish", required = false) Long timestampFinish) {
        if (timestampStart == null && timestampFinish == null) {
            return spentResourcesService.getSpentResourcesByCampaigns(campaigns);
        } else if (timestampStart != null && timestampFinish != null && timestampFinish > timestampStart) {
            return spentResourcesService.getSpentResourcesByCampaignsAndTime(campaigns, timestampStart, timestampFinish);
        } else {
            throw new IllegalArgumentException("Timestamp params should be null or not null both at the same time. timestampFinish should be > timestampStart");
        }
    }
}
