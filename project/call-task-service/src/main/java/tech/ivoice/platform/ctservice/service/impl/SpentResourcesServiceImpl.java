package tech.ivoice.platform.ctservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import tech.ivoice.common.billing.BillingResource;
import tech.ivoice.common.billing.dto.CallTaskSpentResources;
import tech.ivoice.common.billing.dto.CampaignList;
import tech.ivoice.common.billing.dto.CampaignSpentResources;
import tech.ivoice.common.model.calls.CallTask;
import tech.ivoice.common.model.calls.Campaign;
import tech.ivoice.common.repository.CallTaskRepository;
import tech.ivoice.common.repository.CampaignRepository;
import tech.ivoice.platform.ctservice.service.SpentResourcesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class SpentResourcesServiceImpl implements SpentResourcesService {

    private final CampaignRepository campaignRepository;

    private final CallTaskRepository callTaskRepository;

    public SpentResourcesServiceImpl(CampaignRepository campaignRepository, CallTaskRepository callTaskRepository) {
        this.campaignRepository = campaignRepository;
        this.callTaskRepository = callTaskRepository;
    }

    @Override
    public Flux<CampaignSpentResources> getSpentResourcesByCampaigns(CampaignList campaigns) {
        log.debug("SpentResourcesService.getSpentResourcesByCampaigns: parameter: campaigns: {}", campaigns);
        Iterable<Campaign> campaignsIterable = campaignRepository.findAllById(campaigns.getCampaignIds());
        return Flux.fromStream(
                StreamSupport.stream(campaignsIterable.spliterator(), false)
                        .map(campaign -> {
                            List<CallTask> campaignCallTasks = callTaskRepository
                                    .findAllByCampaign(campaign.getId());
                            log.debug("Find {} call tasks for campaign {}", campaignCallTasks.size(), campaign.getId());
                            List<CallTaskSpentResources> callTasksSpentResources = getCallTasksSpentResources(campaignCallTasks);
                            return new CampaignSpentResources(campaign.getId(), callTasksSpentResources, null, null);
                        }));
    }

    @Override
    public Flux<CampaignSpentResources> getSpentResourcesByCampaignsAndTime(CampaignList campaigns, Long timestampStart, Long timestampFinish) {
        Iterable<Campaign> campaignsIterable = campaignRepository.findAllById(campaigns.getCampaignIds());
        return Flux.fromStream(
                StreamSupport.stream(campaignsIterable.spliterator(), false)
                        .map(campaign -> {
                            List<CallTask> campaignCallTasks = callTaskRepository
                                    .findAllByCampaignAndPerformedTimestampBetween(campaign.getId(), timestampStart, timestampFinish);
                            List<CallTaskSpentResources> callTasksSpentResources = getCallTasksSpentResources(campaignCallTasks);
                            return new CampaignSpentResources(campaign.getId(), callTasksSpentResources, timestampStart, timestampFinish);
                        }));
    }

    private List<CallTaskSpentResources> getCallTasksSpentResources(List<CallTask> campaignCallTasks) {
        return campaignCallTasks.stream()
                .map(callTask -> {
                    String callTaskId = callTask.getId();
                    Map<String, Object> spentResources = callTask.getSpentResources();
                    long talkMillis = callTask.getTalkMillis();
                    Map<BillingResource, Double> spentResourcesConverted = convertToSpentResources(spentResources, talkMillis);
                    return new CallTaskSpentResources(callTaskId, spentResourcesConverted);
                })
                .collect(Collectors.toList());
    }

    // преобразование имён ресурсов:
    // collect -> AsrSessionsCount
    // synthesize -> TtsSymbolsGenerated
    private Map<BillingResource, Double> convertToSpentResources(Map<String, Object> callTaskSpentResources, long talkMillis) {
        Map<BillingResource, Double> result = new HashMap<>();

        Object collectValue = callTaskSpentResources.get("collect");
        if (collectValue != null) {
            if (collectValue instanceof Integer) {
                result.put(BillingResource.ASR_SESSIONS_COUNT, (double) (Integer) collectValue);
            } else if (collectValue instanceof Double) {
                result.put(BillingResource.ASR_SESSIONS_COUNT, (Double) collectValue);
            }
        }

        Object synthesizeValue = callTaskSpentResources.get("synthesize");
        if (synthesizeValue != null) {
            if (synthesizeValue instanceof Integer) {
                result.put(BillingResource.TTS_SYMBOLS_GENERATED, (double) (Integer) synthesizeValue);
            } else if (synthesizeValue instanceof Double) {
                result.put(BillingResource.TTS_SYMBOLS_GENERATED, (Double) synthesizeValue);
            }
        }

        // время переводим в секунды
        result.put(BillingResource.DIALOG_TIME, ((double) talkMillis) / 1000.0);

        return result;
    }
}
