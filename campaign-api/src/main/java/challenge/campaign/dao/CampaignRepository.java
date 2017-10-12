package challenge.campaign.dao;

import challenge.campaign.entity.CampaignEntity;
import challenge.campaign.entity.TeamEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data CRUD repository for the Campaign entity.
 */
public interface CampaignRepository extends CrudRepository<CampaignEntity, Long> {

    Optional<CampaignEntity> findOneById(Long aLong);

    List<CampaignEntity> findAllByEndDateGreaterThanEqual(Date date);

    @Query("select c from campaign c where c.startDate >= ?1 and c.endDate <= ?2 order by c.endDate")
    List<CampaignEntity> findAllByStartDateAndEndDateBetween(Date date1, Date date2);

    List<CampaignEntity> findAllByTeamEntity(TeamEntity teamEntity);

    boolean existsByEndDateEqualsAndIdNot(Date date, Long id);

}
