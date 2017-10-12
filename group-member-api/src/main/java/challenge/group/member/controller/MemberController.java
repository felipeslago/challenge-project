package challenge.group.member.controller;

import challenge.group.member.model.MemberModel;
import challenge.group.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * REST controller for managing members.
 * <p>
 * This class accesses the Member service.
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberModel>> retrieveMember() {
        List<MemberModel> members = memberService.retrieveMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberModel> retrieveMember(@NotNull @PathVariable(name = "id") final Long id) {
        MemberModel member = memberService.retrieveMember(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registerMember(@Valid @RequestBody final MemberModel member) {
        memberService.registerMember(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateMember() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
