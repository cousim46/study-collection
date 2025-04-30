package study.lock.distrubuted.redisson;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.lock.common.Counter;
import study.lock.common.CounterRepository;

@SpringBootTest
class RedissonLockTest {
    @Autowired
    RedissonLock redissonLock;
    @Autowired
    CounterRepository counterRepository;

    @BeforeEach
    public void init() {
        counterRepository.save(new Counter(1L));
    }

    @AfterEach
    public void clear() {
        counterRepository.deleteAllInBatch();
    }

    @Test
    void executeRedissonLock() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                redissonLock.executeRedissonLock();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        // then
        Counter counter = counterRepository.findById(1L).get();
        Assertions.assertThat(counter.getCount()).isEqualTo(threadCount);
    }
}