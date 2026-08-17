package transaction.outinbox.scheduler;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import transaction.outinbox.outbox.OutboxService;

@Component
@RequiredArgsConstructor
public class OutboxPollingPublisher {

    private final OutboxService outboxService;

    @Scheduled(fixedDelay = 60000)
    public void polling() {
        LocalDateTime compareDate = LocalDateTime.now().minusMinutes(5);
        outboxService.pollingPublisher(compareDate);
    }

}
