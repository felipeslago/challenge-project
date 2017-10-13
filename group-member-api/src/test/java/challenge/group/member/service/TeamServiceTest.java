package challenge.group.member.service;

import challenge.group.member.exception.TeamNotFoundException;
import challenge.group.member.model.TeamModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private RestTemplate restTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(teamService, "TEAM_URL", "http://localhost:9001/team");
    }

    @Test
    public void itShouldFindTeamById() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "name");

        TeamModel teamModel = mapper.readValue(team.toString(), TeamModel.class);

        when(restTemplate.getForObject("http://localhost:9001/team/1", TeamModel.class)).thenReturn(teamModel);

        assertEquals(Optional.of(teamModel), teamService.findTeamById(1L));
    }

    @Test
    public void itShouldTryToRetrieveCampaignsByTeamIdAndCatchResourceAccessException() {
        when(restTemplate.getForObject("http://localhost:9001/team/1", TeamModel.class)).thenThrow(new ResourceAccessException("exception"));
        teamService.findTeamById(1L);
    }

    @Test(expected = TeamNotFoundException.class)
    public void itShouldTryToRetrieveCampaignsByTeamIdAndThrowTeamNotFoundException() {
        when(restTemplate.getForObject("http://localhost:9001/team/1", TeamModel.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        teamService.findTeamById(1L);
    }

}