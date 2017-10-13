package challenge.group.member.service;

import challenge.group.member.exception.TeamNotFoundException;
import challenge.group.member.model.TeamModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Service class for managing teams.
 */
@Service
public class TeamService extends DefaultResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Value("${team.url}")
    private String TEAM_URL;

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Retrieves a team by its id.
     *
     * @return Optional<TeamModel>
     */
    public Optional<TeamModel> findTeamById(Long id) {
        try {
            TeamModel teamModel = restTemplate.getForObject(TEAM_URL + "/" + id, TeamModel.class);
            return Optional.of(teamModel);
        } catch (ResourceAccessException e) {
            logger.error("Error while retrieving teams.", e);
            return Optional.empty();
        } catch (HttpClientErrorException e) {
            throw new TeamNotFoundException(e.getResponseBodyAsString());
        }
    }

}
