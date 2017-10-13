package challenge.campaign.service;

import challenge.campaign.dao.TeamRepository;
import challenge.campaign.entity.TeamEntity;
import challenge.campaign.exception.TeamNotFoundException;
import challenge.campaign.model.TeamModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing campaigns.
 */
@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Retrieves all teams.
     *
     * @return List<TeamModel>
     */
    public List<TeamModel> retrieveTeams() {
        return teamRepository
                .findAll()
                .stream()
                .map(TeamModel::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves an existing team by its id.
     *
     * @param id the team id
     * @return TeamModel
     */
    public TeamModel retrieveTeam(Long id) {
        Optional<TeamEntity> teamEntity = teamRepository.findOneById(id);
        if(teamEntity.isPresent()) {
            return new TeamModel(teamEntity.get());
        }
        throw new TeamNotFoundException(teamRepository.findAll());
    }

}
