package challenge.campaign.controller;

import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.ResponseError;
import challenge.campaign.model.TeamModel;
import challenge.campaign.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing teams.
 * <p>
 * This class accesses the Team service.
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * GET  /team  : Retrieves all teams.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of teams
     */
    @GetMapping
    public ResponseEntity<List<TeamModel>> retrieveTeams() {
        List<TeamModel> teamModels = teamService.retrieveTeams();
        return new ResponseEntity<>(teamModels, HttpStatus.OK);
    }

    /**
     * GET  /team  : Retrieves a team by its id.
     *
     * @param id the team id
     * @return the ResponseEntity with status 200 (OK) and with body the requested team
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeamModel> retrieveTeams(@NotNull @PathVariable(name = "id") final Long id) {
        TeamModel teamModel = teamService.retrieveTeam(id);
        return new ResponseEntity<>(teamModel, HttpStatus.OK);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    private ResponseEntity<Map<String, String>> teamNotFoundException(TeamNotFoundException e) {
        ResponseError response = new ResponseError();
        response.put("message", "The informed Heart Team Id was not found. Please, use one of listed below.");
        response.put("teams", e.getTeamsEntity().toString());
        return new ResponseEntity<>(response.getResponse(), HttpStatus.NOT_FOUND);
    }

}
