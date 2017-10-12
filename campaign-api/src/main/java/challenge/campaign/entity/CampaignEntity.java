package challenge.campaign.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * A campaign.
 */
@Entity(name = "campaign")
@Table(name = "CAMPAIGN", indexes = {@Index(name = "IDX_END_DATE", columnList = "startDate,endDate")})
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @ManyToOne
    private TeamEntity teamEntity;

    protected CampaignEntity() {
    }

    public CampaignEntity(String name, Date startDate, Date endDate, TeamEntity teamEntity) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamEntity = teamEntity;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TeamEntity getTeamEntity() {
        return teamEntity;
    }

    public void setTeamEntity(TeamEntity teamEntity) {
        this.teamEntity = teamEntity;
    }

    @Override
    public String toString() {
        return String.format(
                "Campaign[id=%d, name='%s', startDate='%s', endDate='%s', heartTeam='%s']",
                id, name, startDate, endDate, teamEntity.getName());
    }
}
