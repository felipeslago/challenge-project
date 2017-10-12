package challenge.campaign.model;

import challenge.campaign.entity.TeamEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * A DTO representing a team.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamModel implements Serializable {

    private static final long serialVersionUID = -3750707074476920413L;

    private Long id;
    private String name;

    private TeamModel() {
    }

    public TeamModel(TeamEntity teamEntity) {
        this.id = teamEntity.getId();
        this.name = teamEntity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format(
                "Team[id=%d, name='%s']",
                id, name);
    }

}
