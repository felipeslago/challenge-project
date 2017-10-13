package challenge.group.member.service;

import challenge.group.member.model.CampaignModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing campaigns.
 */
@Service
public class CampaignService {

    private static final Logger logger = LoggerFactory.getLogger(CampaignService.class);

    @Value("${campaign.team.url}")
    private String CAMPAIGN_TEAM_URL;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Retrieves all campaigns by team id.
     *
     * @return List<CampaignModel>
     */
    public Optional<List<CampaignModel>> retrieveCampaignsByTeamId(Long id) {
        try {
            ResponseEntity<CampaignModel[]> campaigns =
                    restTemplate.getForEntity(CAMPAIGN_TEAM_URL + "/" + id, CampaignModel[].class);
            return Optional.of(Arrays.asList(campaigns.getBody()));
        }  catch (ResourceAccessException|HttpClientErrorException e) {
            logger.error("Error while retrieving teams.", e);
        }
        return Optional.empty();
    }

}
