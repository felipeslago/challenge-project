package challenge.campaign.service;

import challenge.campaign.dao.CampaignRepository;
import challenge.campaign.dao.TeamRepository;
import challenge.campaign.entity.CampaignEntity;
import challenge.campaign.entity.TeamEntity;
import challenge.campaign.exception.CampaignNotFoundException;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.CampaignModel;
import challenge.campaign.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing campaigns.
 */
@Service
@Transactional
public class CampaignService {

    private final Logger logger = LoggerFactory.getLogger(CampaignService.class);
    private final TeamRepository teamRepository;
    private final CampaignRepository campaignRepository;

    public CampaignService(TeamRepository teamRepository, CampaignRepository campaignRepository) {
        this.teamRepository = teamRepository;
        this.campaignRepository = campaignRepository;
    }

    /**
     * Retrieves all campaigns that are still active (end date expired).
     *
     * @return List<CampaignModel>
     */
    public List<CampaignModel> retrieveCampaigns() {
        logger.debug("Retrieving all campaigns");
        return campaignRepository.findAllByEndDateGreaterThanEqual(new Date())
                .stream()
                .map(CampaignModel::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an existing campaign by its id.
     *
     * @param id the campaign id
     * @return CampaignModel
     */
    public CampaignModel retrieveCampaign(Long id) {
        logger.debug("Retrieving campaign with Id: {}", id);
        Optional<CampaignEntity> campaignEntity = campaignRepository.findOneById(id);
        if(campaignEntity.isPresent()) {
            return new CampaignModel(campaignEntity.get());
        }
        throw new CampaignNotFoundException();
    }

    /**
     * Retrieves all campaigns that are still active by team id.
     *
     * @param id the team id
     */
    public List<CampaignModel> retrieveCampaignsByTeamId(Long id) {
        Optional<TeamEntity> teamEntity = teamRepository.findOneById(id);
        if(teamEntity.isPresent()) {
            return campaignRepository.findAllByTeamEntity(teamEntity.get())
                    .stream()
                    .map(CampaignModel::new)
                    .collect(Collectors.toList());
        }
        throw new TeamNotFoundException(teamRepository.findAll());
    }

    /**
     * Register a new campaign if the heart team is valid.
     *
     * @param campaign the campaign to register
     */
    @Async("campaignExecutor")
    public void registerCampaign(CampaignModel campaign) {
        logger.debug("Registering new campaign: {}", campaign.toString());

        Optional<TeamEntity> teamEntity = teamRepository.findOneById(campaign.getHeartTeamId());

        if(teamEntity.isPresent()) {
            List<CampaignEntity> campaignEntities =
                    campaignRepository.findAllByStartDateAndEndDateBetween(campaign.getStartDate(), campaign.getEndDate());

            campaignEntities.forEach(campaignEntity -> {
                do {
                    LocalDate localDate = DateUtils.dateToLocalDate(campaignEntity.getEndDate()).plusDays(1L);
                    campaignEntity.setEndDate(DateUtils.localDateToDate(localDate));
                } while (campaignRepository.existsByEndDateEqualsAndIdNot(campaignEntity.getEndDate(), campaignEntity.getId()) ||
                        campaign.getEndDate().compareTo(campaignEntity.getEndDate()) == 0);

                campaignRepository.save(campaignEntity);
            });

            campaignRepository.save(new CampaignEntity(campaign.getName(), campaign.getStartDate(),
                    campaign.getEndDate(), teamEntity.get()));
        } else {
            throw new TeamNotFoundException(teamRepository.findAll());
        }
    }

    /**
     * Update a new campaign if the heart team is valid.
     *
     * @param campaign the campaign to register
     */
    public void updateCampaign(CampaignModel campaign) {
        logger.debug("Updating campaign: {}", campaign.toString());
        Optional<CampaignEntity> campaignEntity = campaignRepository.findOneById(campaign.getId());

        if(campaignEntity.isPresent()) {
            Optional<TeamEntity> teamEntity = teamRepository.findOneById(campaign.getHeartTeamId());
            if(teamEntity.isPresent()) {
                campaignEntity.get().setName(campaign.getName());
                campaignEntity.get().setStartDate(campaign.getStartDate());
                campaignEntity.get().setEndDate(campaign.getEndDate());
                campaignEntity.get().setTeamEntity(teamEntity.get());
                campaignRepository.save(campaignEntity.get());
            } else {
                throw new TeamNotFoundException(teamRepository.findAll());
            }
        } else {
            throw new CampaignNotFoundException();
        }
    }

    /**
     * Delete an existing campaign.
     *
     * @param id the campaign id
     */
    public void deleteCampaign(Long id) {
        Optional<CampaignEntity> campaignEntity = campaignRepository.findOneById(id);

        if(campaignEntity.isPresent()) {
            logger.debug("Deleting campaign: {}", campaignEntity.toString());
            campaignRepository.delete(campaignEntity.get());
        } else {
            throw new CampaignNotFoundException();
        }
    }

}
