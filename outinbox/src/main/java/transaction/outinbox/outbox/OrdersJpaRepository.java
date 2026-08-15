package transaction.outinbox.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersJpaRepository extends JpaRepository<Orders, Long> {

}
