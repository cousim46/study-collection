package transaction.outinbox.outbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import transaction.outinbox.outbox.enums.MessageStatus;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    Optional<Outbox> findByIdAndMessageStatus(Long outboxId, MessageStatus messageStatus);
    List<Outbox> findAllByMessageStatusAndCreatedAtBefore(MessageStatus messageStatus, LocalDateTime compareDate);



}
