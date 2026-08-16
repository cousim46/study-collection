package transaction.outinbox.outbox.eventlistener;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import transaction.outinbox.outbox.Outbox;
import transaction.outinbox.outbox.OutboxJpaRepository;
import transaction.outinbox.outbox.enums.MessageStatus;
import transaction.outinbox.outbox.event.MessageEvent;

@Component
@RequiredArgsConstructor
public class OutboxEventListener {

    private final OutboxJpaRepository outboxJpaRepository;
    private final RabbitTemplate rabbitTemplate;
    private final static String ORDER_CREATE_EXCHANGE = "event.create.order";

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void messageReady(MessageEvent messageEvent) {
        String eventType = messageEvent.getEventType();
        Outbox outbox = Outbox.ofReady(messageEvent.getAggregateType(),
            messageEvent.getEventId(),
            eventType,
            messageEvent.getPayload(),
            LocalDateTime.now()
        );
        //메시지 발행 준비
        Outbox save = outboxJpaRepository.save(outbox);
        messageEvent.setOutboxId(save.getId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void messagePublish(MessageEvent messageEvent) {
        Long outboxId = messageEvent.getOutboxId();
        Outbox outbox = outboxJpaRepository.findByIdAndMessageStatus(outboxId, MessageStatus.READY)
            .orElseThrow(() -> new RuntimeException("Outbox id not found: " + outboxId));
        //rabbitMQ 메시지 발행
        outbox.updateStatus(MessageStatus.PUBLISHED);

        Message message = MessageBuilder
            .withBody(outbox.getPayload().getBytes(StandardCharsets.UTF_8))
            .setContentType(MessageProperties.CONTENT_TYPE_JSON)
            .build();
        rabbitTemplate.send(ORDER_CREATE_EXCHANGE, "", message);
    }

}
