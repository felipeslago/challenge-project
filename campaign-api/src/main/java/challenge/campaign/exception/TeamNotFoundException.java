package challenge.campaign.exception;

import challenge.campaign.entity.TeamEntity;

import java.util.List;

/**
 * A custom exception for treating not found teams.
 */
public class TeamNotFoundException extends RuntimeException {

    private List<TeamEntity> teamsEntity;

    public TeamNotFoundException(List<TeamEntity> teamsEntity) {
        super();
        this.teamsEntity = teamsEntity;
    }

    public List<TeamEntity> getTeamsEntity() {
        return teamsEntity;
    }
}
