package study.mysqlnamedlock.example.getlock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import study.mysqlnamedlock.domain.Ticket;
import study.mysqlnamedlock.domain.TicketJpaRepository;

@Service
@RequiredArgsConstructor
public class TicketWriteService {

    private final TicketJpaRepository ticketJpaRepository;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long ticketId) {
        Ticket ticket = ticketJpaRepository.findById(ticketId)
            .orElseThrow(IllegalArgumentException::new);
        if(ticket.getAmount() == 0) {
            throw new RuntimeException("티켃이 모두 소진되었습니다.");
        }
        ticket.decrease();
    }
}
