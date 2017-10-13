package challenge.campaign.service;

import challenge.campaign.dao.CampaignRepository;
import challenge.campaign.dao.TeamRepository;
import challenge.campaign.entity.CampaignEntity;
import challenge.campaign.entity.TeamEntity;
import challenge.campaign.exception.CampaignNotFoundException;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.CampaignModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class CampaignServiceTest {

    @InjectMocks
    private CampaignService campaignService;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private WebHookService webHookService;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldRetrieveAllCampaigns() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);

        List<CampaignEntity> campaignEntities = new ArrayList<>();
        campaignEntities.add(campaignEntity);

        List<CampaignModel> campaignModels = campaignEntities.stream()
                .map(CampaignModel::new)
                .collect(Collectors.toList());

        when(campaignRepository.findAllByEndDateGreaterThanEqual(any(Date.class))).thenReturn(campaignEntities);

        assertEquals(campaignModels, campaignService.retrieveCampaigns());
    }

    @Test
    public void itShouldRetrieveACampaignById() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);
        CampaignModel campaignModel = new CampaignModel(campaignEntity);

        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.of(campaignEntity));

        assertEquals(campaignModel, campaignService.retrieveCampaign(1L));
    }

    @Test
    public void itShouldRetrieveAllCampaignsByTeamId() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);

        List<CampaignEntity> campaignEntities = new ArrayList<>();
        campaignEntities.add(campaignEntity);

        List<CampaignModel> campaignModels = campaignEntities.stream()
                .map(CampaignModel::new)
                .collect(Collectors.toList());

        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.of(teamEntity));
        when(campaignRepository.findAllByTeamEntity(any(TeamEntity.class))).thenReturn(campaignEntities);

        assertEquals(campaignModels, campaignService.retrieveCampaignsByTeamId(1L));
    }

    @Test
    public void itShouldRegisterACampaign() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);

        List<CampaignEntity> campaignEntities = new ArrayList<>();
        campaignEntities.add(campaignEntity);

        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.of(teamEntity));
        when(campaignRepository.findAllByStartDateAndEndDateBetween(any(Date.class), any(Date.class))).thenReturn(campaignEntities);

        campaignService.registerCampaign(campaignModel);
    }

    @Test
    public void itShouldUpdateACampaign() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);

        List<CampaignEntity> campaignEntities = new ArrayList<>();
        campaignEntities.add(campaignEntity);

        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.of(campaignEntity));
        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.of(teamEntity));
        when(campaignRepository.findAllByStartDateAndEndDateBetween(any(Date.class), any(Date.class))).thenReturn(campaignEntities);

        campaignService.updateCampaign(campaignModel);
    }

    @Test
    public void itShouldDeleteACampaignById() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);

        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.of(campaignEntity));

        campaignService.deleteCampaign(1L);
    }

    @Test(expected = CampaignNotFoundException.class)
    public void itShouldTryToRetrieveACampaignByIdAndThrowCampaignNotFoundException() throws Exception {
        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        campaignService.retrieveCampaign(1L);
    }

    @Test(expected = TeamNotFoundException.class)
    public void itShouldTryToRetrieveAllCampaignsByTeamIdAndThrowTeamNotFoundException() throws Exception {
        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        campaignService.retrieveCampaignsByTeamId(1L);
    }

    @Test(expected = TeamNotFoundException.class)
    public void itShouldTryToRegisterACampaignAndThrowTeamNotFoundException() throws Exception {
        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        campaignService.registerCampaign(campaignModel);
    }

    @Test(expected = CampaignNotFoundException.class)
    public void itShouldTryToUpdateACampaignAndThrowCampaignNotFoundException() throws Exception {
        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        campaignService.updateCampaign(campaignModel);
    }

    @Test(expected = TeamNotFoundException.class)
    public void itShouldTryToUpdateACampaignAndThrowTeamNotFoundException() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("teamEntity", team);

        CampaignEntity campaignEntity = mapper.readValue(campaign.toString(), CampaignEntity.class);

        JSONObject campaignRequest = new JSONObject();
        campaignRequest.put("name", "name");
        campaignRequest.put("startDate", "2017-11-01");
        campaignRequest.put("endDate", "2017-11-03");
        campaignRequest.put("heartTeamId", 1L);

        CampaignModel campaignModel = mapper.readValue(campaignRequest.toString(), CampaignModel.class);

        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.of(campaignEntity));
        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        campaignService.updateCampaign(campaignModel);
    }

    @Test(expected = CampaignNotFoundException.class)
    public void itShouldTryToDeleteACampaignByIdAndThrowCampaignNotFoundException() throws Exception {
        when(campaignRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        campaignService.deleteCampaign(1L);
    }

}