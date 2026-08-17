package transaction.outinbox.outbox;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.outinbox.outbox.enums.MessageStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final static String ORDER_CREATE_EXCHANGE = "event.create.order";
    private final OutboxJpaRepository outboxJpaRepository;
    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public void pollingPublisher(LocalDateTime compareDate) {
        outboxJpaRepository.findAllByMessageStatusAndCreatedAtBefore(MessageStatus.READY, compareDate)
            .forEach(outbox -> {
                try {
                    Message message = MessageBuilder
                        .withBody(outbox.getPayload().getBytes(StandardCharsets.UTF_8))
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .build();
                    rabbitTemplate.send(ORDER_CREATE_EXCHANGE, "", message);
                    outbox.updateStatus(MessageStatus.PUBLISHED);
                } catch (Exception e) {
                    log.error("Outbox 메시지 발행 실패 id: {}", outbox.getId(), e);
                }
            });
    }

}
