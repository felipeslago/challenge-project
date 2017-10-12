package challenge.group.member.entity;

import challenge.group.member.model.CampaignModel;
import challenge.group.member.model.TeamModel;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "MEMBER")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Date dateOfBirth;
    private Long heartTeam;
    @ElementCollection
    private List<Long> campaigns;

    protected MemberEntity() {
    }

    public MemberEntity(String name, String email, Date dateOfBirth, Optional<TeamModel> heartTeam) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        heartTeam.ifPresent(team -> this.heartTeam = team.getId());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getHeartTeam() {
        return heartTeam;
    }

    public void setHeartTeam(Long heartTeam) {
        this.heartTeam = heartTeam;
    }

    public List<Long> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Long> campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public String toString() {
        return String.format(
                "Member[id=%d, name='%s', email='%s', dateOfBirth='%s', heartTeam=%d, campaigns='%s']",
                id, name, email, dateOfBirth, heartTeam, campaigns.toString());
    }
}
