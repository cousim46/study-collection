package study.ratelimiter.leaky;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeakyBucketSchedule {

    private final LeakyBucketService leakyBucketService;

    @Scheduled(cron = "*/3 * * * * *")
    public void consume() {
        leakyBucketService.consume();
    }
}
