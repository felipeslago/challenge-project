package challenge.campaign.controller;

import challenge.campaign.entity.TeamEntity;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.TeamModel;
import challenge.campaign.service.TeamService;
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

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeamControllerTest {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
    }

    @Test
    public void itShouldRetrieveAllTeams() throws Exception {
        List<TeamModel> teamModels = new ArrayList<>();

        String json = mapper.writeValueAsString(teamModels);

        when(teamService.retrieveTeams()).thenReturn(teamModels);

        mockMvc.perform(get("/team")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldRetrieveASpecificTeamById() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1L);
        request.put("name", "name");

        TeamModel teamModel = mapper.readValue(request.toString(), TeamModel.class);

        String json = mapper.writeValueAsString(teamModel);

        when(teamService.retrieveTeam(anyLong())).thenReturn(teamModel);

        mockMvc.perform(get("/team/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldThrowTeamNotFoundException() throws Exception {
        JSONObject request = new JSONObject();
        request.put("id", 1L);
        request.put("name", "name");

        TeamModel teamModel = mapper.readValue(request.toString(), TeamModel.class);
        List<TeamEntity> teamEntities = new ArrayList<>();

        String json = mapper.writeValueAsString(teamModel);

        when(teamService.retrieveTeam(anyLong())).thenThrow(new TeamNotFoundException(teamEntities));

        mockMvc.perform(get("/team/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

}