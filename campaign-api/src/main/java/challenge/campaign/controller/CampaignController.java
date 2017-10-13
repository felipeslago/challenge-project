package challenge.campaign.controller;

import challenge.campaign.exception.CampaignNotFoundException;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.CampaignModel;
import challenge.campaign.model.ResponseError;
import challenge.campaign.service.CampaignService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing campaigns.
 * <p>
 * This class accesses the Campaign service.
 */
@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * GET  /campaign  : Retrieves all campaigns that are still active (end date expired).
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of active campaigns
     */
    @GetMapping
    public ResponseEntity<List<CampaignModel>> retrieveAllCampaigns() {
        List<CampaignModel> campaigns = campaignService.retrieveCampaigns();
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    /**
     * GET  /campaign  : Retrieves a campaign by its id.
     *
     * @param id the campaign id
     * @return the ResponseEntity with status 200 (OK) and with body the requested campaign
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<CampaignModel> retrieveCampaign(@NotNull @PathVariable(value = "id") final Long id) {
        CampaignModel campaign = campaignService.retrieveCampaign(id);
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }

    /**
     * GET  /campaign/team  : Retrieves all active campaigns for a team id.
     *
     * @param id the team id
     * @return the ResponseEntity with status 200 (OK) and with body the list of active campaigns
     */
    @GetMapping(value = "/team/{id}")
    public ResponseEntity<List<CampaignModel>> retrieveCampaignsByTeamId(@NotNull @PathVariable(value = "id") final Long id) {
        List<CampaignModel> campaign = campaignService.retrieveCampaignsByTeamId(id);
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }

    /**
     * POST  /campaign  : Register a new campaign.
     * <p>
     * Creates a new campaign if the heart team is valid
     *
     * @return the ResponseEntity with status 200 (OK) and with null body
     */
    @PostMapping
    public ResponseEntity<Void> registerCampaing(@Valid @RequestBody CampaignModel campaign) {
        campaignService.registerCampaign(campaign);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * PUT  /campaign  : Update an existing campaign.
     * <p>
     * Update an existing campaign if the heart team is valid
     *
     * @return the ResponseEntity with status 204 (NO CONTENT) and with null body
     */
    @PutMapping
    public ResponseEntity<Void> updateCampaign(@Valid @RequestBody CampaignModel campaign) {
        campaignService.updateCampaign(campaign);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * DELETE  /campaign  : Delete an existing campaign.
     * <p>
     * Delete an existing campaign if the campaign id exists
     *
     * @return the ResponseEntity with status 204 (NO CONTENT) and with null body
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCampaign(@NotNull @PathVariable(value = "id") Long id) {
        campaignService.deleteCampaign(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    private ResponseEntity<Map<String, Object>> teamNotFoundException(TeamNotFoundException e) {
        ResponseError response = new ResponseError();
        response.put("message", "The informed Heart Team Id was not found. Please, use one of listed below.");
        response.put("teams", e.getTeamsEntity().toString());
        return new ResponseEntity<>(response.getResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CampaignNotFoundException.class)
    private ResponseEntity<Map<String, Object>> campaignNotFoundException(CampaignNotFoundException e) {
        ResponseError response = new ResponseError();
        response.put("message", "The informed Campaign Id was not found.");
        return new ResponseEntity<>(response.getResponse(), HttpStatus.NOT_FOUND);
    }

}
