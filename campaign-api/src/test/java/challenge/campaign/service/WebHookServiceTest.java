package challenge.campaign.service;

import challenge.campaign.model.CampaignModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WebHookServiceTest {

    @InjectMocks
    private WebHookService webHookService;

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(webHookService, "webhookUrl", "http://localhost:9002/webhook");
    }

    @Test
    public void itShouldSendCampaignUpdates() throws Exception {
        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(restTemplate.postForEntity("http://localhost:9002/webhook", campaignModel, Void.class)).thenReturn(ResponseEntity.ok(null));

        webHookService.sendCampaignUpdates(campaignModel);
    }

    @Test
    public void itShouldTryToSendCampaignUpdatesAndCatchResourceAccessException() throws Exception {
        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(restTemplate.postForEntity("http://localhost:9002/webhook", campaignModel, Void.class)).thenThrow(new ResourceAccessException("exception"));

        webHookService.sendCampaignUpdates(campaignModel);
    }

    @Test
    public void itShouldTryToSendCampaignUpdatesAndCatchHttpClientErrorException() throws Exception {
        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(restTemplate.postForEntity("http://localhost:9002/webhook", campaignModel, Void.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        webHookService.sendCampaignUpdates(campaignModel);
    }

}