package challenge.group.member.controller;

import challenge.group.member.exception.MemberNotFoundException;
import challenge.group.member.exception.TeamNotFoundException;
import challenge.group.member.model.MemberModel;
import challenge.group.member.model.ResponseError;
import challenge.group.member.service.MemberService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing members.
 * <p>
 * This class accesses the Member service.
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * GET  /member  : Retrieves all member.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of members
     */
    @GetMapping
    public ResponseEntity<List<MemberModel>> retrieveMember() {
        List<MemberModel> members = memberService.retrieveMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    /**
     * GET  /member  : Retrieves a member by its id.
     *
     * @param id the member id
     * @return the ResponseEntity with status 200 (OK) and with body the requested member
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberModel> retrieveMember(@NotNull @PathVariable(name = "id") final Long id) {
        MemberModel member = memberService.retrieveMember(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    /**
     * POST  /member  : Register a new member.
     * <p>
     * Creates a new member if the heart team is valid
     *
     * @return the ResponseEntity with status 200 (OK) and with null body
     */
    @PostMapping
    public ResponseEntity<Void> registerMember(@Valid @RequestBody final MemberModel member) {
        memberService.registerMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    private ResponseEntity<Map<String, Object>> teamNotFoundException(TeamNotFoundException e) {
        JSONObject jsonObject = new JSONObject(e.getMessage());
        ResponseError responseError = new ResponseError(jsonObject);
        return new ResponseEntity<>(responseError.getResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    private ResponseEntity<Map<String, Object>> memberNotFoundException(MemberNotFoundException e) {
        ResponseError response = new ResponseError();
        response.put("message", "The informed Member Id was not found.");
        return new ResponseEntity<>(response.getResponse(), HttpStatus.NOT_FOUND);
    }

}
