package study.lock.distrubuted;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.lock.common.Counter;
import study.lock.common.CounterRepository;

@SpringBootTest
class MySqlNamedLockTest {

    @Autowired
    private MySqlNamedLock mySqlNamedLock;
    @Autowired
    private CounterRepository counterRepository;

    @BeforeEach
    public void init() {
        counterRepository.save(new Counter(1L));
    }

    @AfterEach
    public void clear() {
        counterRepository.deleteAllInBatch();
    }

    @Test
    void executeMySqlNamedLock() throws InterruptedException {
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch doneSignal = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(
                () ->
                {
                    mySqlNamedLock.updateCounter();
                    doneSignal.countDown();
                }
            );
        }
        doneSignal.await();
        executorService.shutdown();
        Counter counter = counterRepository.findById(1L).get();
        Assertions.assertThat(counter.getCount()).isEqualTo(5);
    }
}