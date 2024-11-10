package transaction.propagation.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import transaction.propagation.domain.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);
}
