package transaction.outinbox.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import transaction.outinbox.outbox.enums.AggregateType;
import transaction.outinbox.outbox.enums.MessageStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AggregateType aggregateType;
    private Long aggregateId;
    private String eventType;
    private String payload;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static Outbox ofReady(AggregateType aggregateType,
        Long aggregateId,
        String eventType,
        String payload,
        LocalDateTime createdAt
    ) {
        Outbox outbox = new Outbox();
        outbox.aggregateType = aggregateType;
        outbox.aggregateId = aggregateId;
        outbox.eventType = eventType;
        outbox.payload = payload;
        outbox.messageStatus = MessageStatus.READY;
        outbox.createdAt = createdAt;
        return outbox;
    }

    public void updateStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }
}
