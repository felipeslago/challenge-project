package challenge.campaign.dao;

import challenge.campaign.entity.TeamEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data CRUD repository for the Team entity.
 */
public interface TeamRepository extends CrudRepository<TeamEntity, Long> {

    Optional<TeamEntity> findOneById(Long aLong);

    List<TeamEntity> findAll();

}
