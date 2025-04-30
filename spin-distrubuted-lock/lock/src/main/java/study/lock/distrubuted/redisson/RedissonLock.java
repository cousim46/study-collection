package study.lock.distrubuted.redisson;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.lock.distrubuted.mysqlnamedlock.CounterService;

@Service
@RequiredArgsConstructor
public class RedissonLock {

    private final RedissonCounterService redissonCounterService;
    private final RedissonClient redissonClient;
    private static final String REDISSON_COUNT_LOCK_PREFIX = "count:";

    public void executeRedissonLock() {
        long countId = 1L;
        RLock lock = redissonClient.getLock(
            String.format("%s%d", REDISSON_COUNT_LOCK_PREFIX, countId));
        String currentName = Thread.currentThread().getName();
        try {
            boolean isLock = lock.tryLock(3, 1, TimeUnit.SECONDS);
            if (!isLock) {
                System.out.printf("락 실패 스레드명 : %s", currentName);
                return;
            }
            System.out.printf("락 획득 스레드명 : %s", currentName);
            redissonCounterService.update(countId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
