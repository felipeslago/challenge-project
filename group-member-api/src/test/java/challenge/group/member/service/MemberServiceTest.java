package challenge.group.member.service;

import challenge.group.member.dao.MemberRepository;
import challenge.group.member.entity.MemberEntity;
import challenge.group.member.exception.MemberNotFoundException;
import challenge.group.member.exception.TeamNotFoundException;
import challenge.group.member.model.CampaignModel;
import challenge.group.member.model.MemberModel;
import challenge.group.member.model.TeamModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TeamService teamService;

    @Mock
    private CampaignService campaignService;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldRetrieveAllMembers() throws Exception {
        List<Long> campaigns = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        JSONObject member = new JSONObject();
        member.put("id", 1L);
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        MemberEntity memberEntity = mapper.readValue(member.toString(), MemberEntity.class);
        memberEntity.setCampaigns(campaigns);

        List<MemberEntity> memberEntities = new ArrayList<>();
        memberEntities.add(memberEntity);

        List<MemberModel> memberModels = memberEntities.stream().map(MemberModel::new).collect(Collectors.toList());

        when(memberRepository.findAll()).thenReturn(memberEntities);

        assertEquals(memberModels, memberService.retrieveMembers());
    }

    @Test
    public void itShouldRetrieveASpecificMember() throws Exception {
        List<Long> campaigns = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        JSONObject member = new JSONObject();
        member.put("id", 1L);
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        MemberEntity memberEntity = mapper.readValue(member.toString(), MemberEntity.class);
        memberEntity.setCampaigns(campaigns);

        MemberModel memberModel = new MemberModel(memberEntity);

        when(memberRepository.findOneById(anyLong())).thenReturn(Optional.of(memberEntity));

        assertEquals(memberModel, memberService.retrieveMember(1L));
    }

    @Test
    public void itShouldRegisterANewMember() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "name");

        TeamModel teamModel = mapper.readValue(team.toString(), TeamModel.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("heartTeamId", 1L);
        campaign.put("heartTeam", "heart team");

        CampaignModel campaignModel = mapper.readValue(campaign.toString(), CampaignModel.class);

        List<CampaignModel> campaignModels = new ArrayList<>();
        campaignModels.add(campaignModel);

        List<Long> campaigns = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        JSONObject member = new JSONObject();
        member.put("id", 1L);
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        MemberEntity memberEntity = mapper.readValue(member.toString(), MemberEntity.class);
        memberEntity.setCampaigns(campaigns);

        JSONObject memberRequest = new JSONObject();
        memberRequest.put("name", "name");
        memberRequest.put("email", "email");
        memberRequest.put("dateOfBirth", "2017-11-01");
        memberRequest.put("heartTeam", 1L);

        MemberModel memberModel = mapper.readValue(memberRequest.toString(), MemberModel.class);

        when(teamService.findTeamById(anyLong())).thenReturn(Optional.of(teamModel));
        when(campaignService.retrieveCampaignsByTeamId(anyLong())).thenReturn(Optional.of(campaignModels));
        when(memberRepository.findOneByEmail(anyString())).thenReturn(Optional.of(memberEntity));

        memberService.registerMember(memberModel);
    }

    @Test
    public void itShouldRegisterAnExistingMember() throws Exception {
        JSONObject team = new JSONObject();
        team.put("id", 1L);
        team.put("name", "name");

        TeamModel teamModel = mapper.readValue(team.toString(), TeamModel.class);

        JSONObject campaign = new JSONObject();
        campaign.put("id", 1L);
        campaign.put("name", "name");
        campaign.put("startDate", "2017-11-01");
        campaign.put("endDate", "2017-11-03");
        campaign.put("heartTeamId", 1L);
        campaign.put("heartTeam", "heart team");

        CampaignModel campaignModel = mapper.readValue(campaign.toString(), CampaignModel.class);

        List<CampaignModel> campaignModels = new ArrayList<>();
        campaignModels.add(campaignModel);

        JSONObject memberRequest = new JSONObject();
        memberRequest.put("name", "name");
        memberRequest.put("email", "email");
        memberRequest.put("dateOfBirth", "2017-11-01");
        memberRequest.put("heartTeam", 1L);

        MemberModel memberModel = mapper.readValue(memberRequest.toString(), MemberModel.class);

        List<Long> campaigns = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        JSONObject member = new JSONObject();
        member.put("id", 1L);
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        MemberEntity memberEntity = mapper.readValue(member.toString(), MemberEntity.class);
        memberEntity.setCampaigns(campaigns);

        when(teamService.findTeamById(anyLong())).thenReturn(Optional.of(teamModel));
        when(campaignService.retrieveCampaignsByTeamId(anyLong())).thenReturn(Optional.of(campaignModels));
        when(memberRepository.findOneByEmail(anyString())).thenReturn(Optional.empty());
        when(memberRepository.save(any(MemberEntity.class))).thenReturn(memberEntity);

        memberService.registerMember(memberModel);
    }

    @Test(expected = MemberNotFoundException.class)
    public void itShouldTryToRetrieveAMemberAndThrowMemberNotFoundException() {
        when(memberRepository.findOneById(anyLong())).thenReturn(Optional.empty());
        memberService.retrieveMember(1L);
    }

    @Test(expected = TeamNotFoundException.class)
    public void itShouldTryToRegisterAMemberAndThrowTeamNotFoundException() throws Exception {
        JSONObject memberRequest = new JSONObject();
        memberRequest.put("name", "name");
        memberRequest.put("email", "email");
        memberRequest.put("dateOfBirth", "2017-11-01");
        memberRequest.put("heartTeam", 1L);

        MemberModel memberModel = mapper.readValue(memberRequest.toString(), MemberModel.class);

        when(teamService.findTeamById(anyLong())).thenThrow(new TeamNotFoundException("exception"));
        memberService.registerMember(memberModel);
    }

}