package challenge.group.member.model;

import challenge.group.member.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO representing a member.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberModel implements Serializable {

    private static final long serialVersionUID = 7727950116842052587L;

    private Long id;
    private String name;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt-BR", timezone = "Brazil/East")
    private Date dateOfBirth;
    private Long heartTeam;

    private MemberModel() {
    }

    public MemberModel(MemberEntity memberEntity) {
        this.id = memberEntity.getId();
        this.name = memberEntity.getName();
        this.email = memberEntity.getEmail();
        this.dateOfBirth = memberEntity.getDateOfBirth();
        this.heartTeam = memberEntity.getHeartTeam();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getHeartTeam() {
        return heartTeam;
    }

    @Override
    public boolean equals(Object obj) {
        MemberModel memberModel = (MemberModel) obj;
        return this.id == memberModel.getId() &&
                this.name == memberModel.getName() &&
                this.email == memberModel.getEmail() &&
                this.dateOfBirth == memberModel.getDateOfBirth() &&
                this.heartTeam == memberModel.getHeartTeam();
    }
}
