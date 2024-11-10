package transaction.propagation.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import transaction.propagation.domain.entity.Member;
import transaction.propagation.domain.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}
