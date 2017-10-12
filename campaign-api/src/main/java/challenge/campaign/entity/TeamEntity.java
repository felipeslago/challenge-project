package challenge.campaign.entity;

import javax.persistence.*;

/**
 * A team.
 */
@Entity(name = "team")
@Table(name = "TEAM")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    protected TeamEntity() {
    }

    public TeamEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Team[id=%d, name='%s']",
                id, name);
    }
}
