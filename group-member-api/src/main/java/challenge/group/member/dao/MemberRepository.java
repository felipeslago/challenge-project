package challenge.group.member.dao;

import challenge.group.member.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends CrudRepository<MemberEntity, Long> {

    Optional<MemberEntity> findOneById(Long aLong);

    List<MemberEntity> findAll();

    Optional<MemberEntity> findOneByEmail(String email);

}
