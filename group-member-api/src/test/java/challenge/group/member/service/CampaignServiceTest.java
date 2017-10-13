package challenge.group.member.service;

import challenge.group.member.model.CampaignModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CampaignServiceTest {

    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(campaignService, "CAMPAIGN_TEAM_URL", "http://localhost:9001/campaign/team");
    }

    @Test
    public void itShouldRetrieveCampaignsByTeamId() throws Exception {
        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("heartTeamId", 1L);
        campaign.put("heartTeam", "heart team");

        CampaignModel campaignModel = mapper.readValue(campaign.toString(), CampaignModel.class);

        CampaignModel[] campaignModels = new CampaignModel[1];
        campaignModels[0] = campaignModel;

        Optional<List<CampaignModel>> campaigns = Optional.of(Arrays.asList(campaignModels));

        when(restTemplate.getForEntity("http://localhost:9001/campaign/team/1", CampaignModel[].class)).thenReturn(ResponseEntity.ok(campaignModels));

        assertEquals(campaigns, campaignService.retrieveCampaignsByTeamId(1L));
    }

    @Test
    public void itShouldTryToRetrieveCampaignsByTeamIdAndCatchResourceAccessException() {
        when(restTemplate.getForEntity("http://localhost:9001/campaign/team/1", CampaignModel[].class)).thenThrow(new ResourceAccessException("exception"));
        campaignService.retrieveCampaignsByTeamId(1L);
    }

    @Test
    public void itShouldTryToRetrieveCampaignsByTeamIdAndCatchHttpClientErrorException() {
        when(restTemplate.getForEntity("http://localhost:9001/campaign/team/1", CampaignModel[].class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        campaignService.retrieveCampaignsByTeamId(1L);
    }

}