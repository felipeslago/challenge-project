package challenge.campaign.model;

import challenge.campaign.entity.CampaignEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * A DTO representing a campaign.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignModel implements Serializable {

    private static final long serialVersionUID = -5153294576009928879L;

    private Long id;
    @NotNull
    @Size(max = 80)
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private Date endDate;
    @NotNull
    private Long heartTeamId;
    private String heartTeam;

    private CampaignModel() {
    }

    public CampaignModel(Long id, String name, Date startDate, Date endDate, Long heartTeamId, String heartTeam) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.heartTeamId = heartTeamId;
        this.heartTeam = heartTeam;
    }

    public CampaignModel(CampaignEntity campaignEntity) {
        this.id = campaignEntity.getId();
        this.name = campaignEntity.getName();
        this.startDate = campaignEntity.getStartDate();
        this.endDate = campaignEntity.getEndDate();
        this.heartTeamId = campaignEntity.getTeamEntity().getId();
        this.heartTeam = campaignEntity.getTeamEntity().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Long getHeartTeamId() {
        return heartTeamId;
    }

    public String getHeartTeam() {
        return heartTeam;
    }

    @Override
    public String toString() {
        return String.format(
                "Campaign[id=%d, name='%s', startDate='%s', endDate='%s', heartTeamId=%d, heartTeam='%s']",
                id, name, startDate, endDate, heartTeamId, heartTeam);
    }
}
