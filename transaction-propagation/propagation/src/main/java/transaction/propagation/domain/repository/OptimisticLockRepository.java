package transaction.propagation.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import transaction.propagation.domain.entity.OptimisticLock;

public interface OptimisticLockRepository extends JpaRepository<OptimisticLock, Long> {

    Optional<OptimisticLock> findByName(String name);

}
