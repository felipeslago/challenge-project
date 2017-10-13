package challenge.campaign.service;

import challenge.campaign.model.CampaignModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for sending web hooks updates.
 */
@Service
public class WebHookService {

    private final Logger logger = LoggerFactory.getLogger(WebHookService.class);

    @Value("${webhook.url")
    private String webhookUrl;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Send updates for updated campaigns.
     *
     * @param campaign the updated campaign
     */
    @Async("campaignExecutor")
    public void sendCampaignUpdates(CampaignModel campaign) {
        try {
            restTemplate.postForEntity(webhookUrl, campaign, Void.class);
        } catch (ResourceAccessException | HttpClientErrorException e) {
            logger.error("Error while sending web hook.", e);
        }
    }

}
