package challenge.campaign.service;

import challenge.campaign.dao.TeamRepository;
import challenge.campaign.entity.TeamEntity;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.TeamModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

public class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldRetrieveAllTeams() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);

        List<TeamEntity> teamEntities = new ArrayList<>();
        teamEntities.add(teamEntity);

        List<TeamModel> teamModels = teamEntities.stream()
                .map(TeamModel::new)
                .collect(Collectors.toList());

        when(teamRepository.findAll()).thenReturn(teamEntities);

        assertEquals(teamModels,teamService.retrieveTeams());
    }

    @Test
    public void itShouldRetrieveASpecificTeamById() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "team");

        TeamEntity teamEntity = mapper.readValue(team.toString(), TeamEntity.class);
        TeamModel teamModel = new TeamModel(teamEntity);

        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.of(teamEntity));

        assertEquals(teamModel, teamService.retrieveTeam(1L));
    }

    @Test(expected = TeamNotFoundException.class)
    public void itShouldRetrieveASpecificTeamByIdAndThrowTeamNotFoundException() throws Exception {
        when(teamRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        teamService.retrieveTeam(1L);
    }

}