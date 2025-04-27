package study.lock.spin.redis;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.lock.common.Counter;
import study.lock.spin.SpinLock;
import study.lock.spin.java.JavaSpinLock;

@SpringBootTest
class RedisSpinLockTest {
    @Autowired
    private RedisSpinLock redisSpinLock;


    @Test
    void executeSpinLock() throws InterruptedException {
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Counter counter = new Counter();
        CountDownLatch doneSignal = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                redisSpinLock.executeSpinLock(counter);
                doneSignal.countDown();
            });
        }
        doneSignal.await();
        executorService.shutdown();
        Assertions.assertThat(counter.getCount()).isEqualTo(threadCount);
    }
}