package challenge.group.member.service;

import challenge.group.member.dao.MemberRepository;
import challenge.group.member.entity.MemberEntity;
import challenge.group.member.exception.MemberNotFoundException;
import challenge.group.member.model.CampaignModel;
import challenge.group.member.model.MemberModel;
import challenge.group.member.model.TeamModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing members.
 */
@Service
@Transactional
public class MemberService {

    private MemberRepository memberRepository;
    private TeamService teamService;
    private CampaignService campaignService;

    public MemberService(MemberRepository memberRepository, TeamService teamService, CampaignService campaignService) {
        this.memberRepository = memberRepository;
        this.teamService = teamService;
        this.campaignService = campaignService;
    }

    /**
     * Retrieves all members.
     *
     * @return List<MemberModel>
     */
    public List<MemberModel> retrieveMembers() {
        return memberRepository
                .findAll()
                .stream()
                .map(MemberModel::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an existing member by its id.
     *
     * @param id the member id
     * @return MemberModel
     */
    public MemberModel retrieveMember(Long id) {
        Optional<MemberEntity> memberEntity = memberRepository.findOneById(id);
        if(memberEntity.isPresent()) {
            return new MemberModel(memberEntity.get());
        }
        throw new MemberNotFoundException();
    }

    /**
     * Register a new member if the heart team is valid.
     *
     * @param member the member to register
     */
    public void registerMember(MemberModel member) {
        Optional<TeamModel> teamModel = teamService.findTeamById(member.getHeartTeam());

        Optional<List<CampaignModel>> campaigns = Optional.empty();

        if(teamModel.isPresent()) {
            campaigns = campaignService.retrieveCampaignsByTeamId(teamModel.get().getId());
        }

        Optional<MemberEntity> memberEntity = memberRepository.findOneByEmail(member.getEmail());

        if(memberEntity.isPresent()) {
            memberEntity.get().setCampaigns(campaigns.get().stream().map(CampaignModel::getId).collect(Collectors.toList()));
            memberRepository.save(memberEntity.get());
        } else {
            MemberEntity newMember = memberRepository.save(
                    new MemberEntity(member.getName(), member.getEmail(), member.getDateOfBirth(), teamModel));

            campaigns.ifPresent(campaignList -> {
                List<Long> campaignsId = campaignList.stream().map(CampaignModel::getId).collect(Collectors.toList());
                newMember.setCampaigns(campaignsId);
                memberRepository.save(newMember);
            });
        }
    }
}
