package challenge.group.member.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * A DTO representing a team.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamModel implements Serializable {

    private static final long serialVersionUID = -7209909048242677013L;

    private Long id;
    private String name;

    private TeamModel() {
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
