package study.mysqlnamedlock.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<Ticket, Long> {
}
