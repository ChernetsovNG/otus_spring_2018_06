package tech.ivoice.platform.ctservice.service;

import reactor.core.publisher.Flux;
import tech.ivoice.common.billing.dto.CampaignList;
import tech.ivoice.common.billing.dto.CampaignSpentResources;

public interface SpentResourcesService {

    Flux<CampaignSpentResources> getSpentResourcesByCampaigns(CampaignList campaigns);

    Flux<CampaignSpentResources> getSpentResourcesByCampaignsAndTime(CampaignList campaigns, Long timestampStart, Long timestampFinish);
}
