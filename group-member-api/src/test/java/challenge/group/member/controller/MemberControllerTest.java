package challenge.group.member.controller;

import challenge.group.member.exception.MemberNotFoundException;
import challenge.group.member.exception.TeamNotFoundException;
import challenge.group.member.model.MemberModel;
import challenge.group.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    public void itShouldRetrieveAllMembers() throws Exception {
        List<MemberModel> memberModels = new ArrayList<>();
        String json = mapper.writeValueAsString(memberModels);

        when(memberService.retrieveMembers()).thenReturn(memberModels);

        mockMvc.perform(get("/member")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldRetrieveASpecificMemberById() throws Exception {
        JSONObject member = new JSONObject();
        member.put("id", 1L);
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        MemberModel memberModel = mapper.readValue(member.toString(), MemberModel.class);

        String json = mapper.writeValueAsString(memberModel);

        when(memberService.retrieveMember(anyLong())).thenReturn(memberModel);

        mockMvc.perform(get("/member/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string(json));
    }

    @Test
    public void itShouldRegisterAMember() throws Exception {
        JSONObject member = new JSONObject();
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        MemberModel memberModel = mapper.readValue(member.toString(), MemberModel.class);

        String json = mapper.writeValueAsString(memberModel);

        doNothing().when(memberService).registerMember(any(MemberModel.class));

        mockMvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void itShouldThrowTeamNotFoundException() throws Exception {
        JSONObject member = new JSONObject();
        member.put("name", "name");
        member.put("email", "email");
        member.put("dateOfBirth", "2017-11-01");
        member.put("heartTeam", 1L);

        doThrow(new TeamNotFoundException("exception")).when(memberService).registerMember(any(MemberModel.class));

        mockMvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(member.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void itShouldThrowMemberNotFoundException() throws Exception {
        when(memberService.retrieveMember(anyLong())).thenThrow(new MemberNotFoundException());

        mockMvc.perform(get("/member/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isNotFound());
    }

}