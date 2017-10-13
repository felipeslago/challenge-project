package challenge.campaign.controller;

import challenge.campaign.entity.TeamEntity;
import challenge.campaign.exception.CampaignNotFoundException;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.CampaignModel;
import challenge.campaign.service.CampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CampaignControllerTest {

    @InjectMocks
    private CampaignController campaignController;

    @Mock
    private CampaignService campaignService;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
    }

    @Test
    public void itShouldRetrieveAllCampaigns() throws Exception {
        List<CampaignModel> campaigns = new ArrayList<>();
        when(campaignService.retrieveCampaigns()).thenReturn(campaigns);

        String json = mapper.writeValueAsString(campaigns);

        mockMvc.perform(get("/campaign")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldRetrieveASpecificCampaignById() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1L);
        request.put("name", "name");
        request.put("startDate", "2017-11-01");
        request.put("endDate", "2017-11-03");
        request.put("heartTeamId", 1L);
        request.put("heartTeam", "heart team");

        CampaignModel campaign = mapper.readValue(request.toString(), CampaignModel.class);

        when(campaignService.retrieveCampaign(anyLong())).thenReturn(campaign);

        String json = mapper.writeValueAsString(campaign);

        mockMvc.perform(get("/campaign/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldRetrieveAllActiveCampaignsByTeamById() throws Exception {
        List<CampaignModel> campaigns = new ArrayList<>();
        when(campaignService.retrieveCampaignsByTeamId(anyLong())).thenReturn(campaigns);

        String json = mapper.writeValueAsString(campaigns);

        mockMvc.perform(get("/campaign/team/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldRegisterACampaign() throws Exception {
        JSONObject request = new JSONObject();
        request.put("name", "name");
        request.put("startDate", "2017-11-01");
        request.put("endDate", "2017-11-03");
        request.put("heartTeamId", 1L);

        doNothing().when(campaignService).registerCampaign(any(CampaignModel.class));

        mockMvc.perform(post("/campaign")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(request.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void itShouldUpdateACampaign() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1L);
        request.put("name", "name");
        request.put("startDate", "2017-11-01");
        request.put("endDate", "2017-11-03");
        request.put("heartTeamId", 1L);

        doNothing().when(campaignService).updateCampaign(any(CampaignModel.class));

        mockMvc.perform(put("/campaign")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(request.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void itShouldDeleteACampaign() throws Exception {
        doNothing().when(campaignService).deleteCampaign(anyLong());

        mockMvc.perform(delete("/campaign/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void itShouldThrowTeamNotFoundException() throws Exception {
        List<TeamEntity> teamEntities = new ArrayList<>();
        when(campaignService.retrieveCampaignsByTeamId(anyLong())).thenThrow(new TeamNotFoundException(teamEntities));

        mockMvc.perform(get("/campaign/team/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void itShouldThrowCampaignNotFoundException() throws Exception {
        when(campaignService.retrieveCampaign(anyLong())).thenThrow(new CampaignNotFoundException());

        mockMvc.perform(get("/campaign/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

}