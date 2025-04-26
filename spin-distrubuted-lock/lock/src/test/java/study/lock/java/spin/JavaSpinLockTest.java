package study.lock.java.spin;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import study.lock.common.Counter;
import study.lock.spin.SpinLock;
import study.lock.spin.java.JavaSpinLock;

class JavaSpinLockTest {

    @Test
    void executeJavaSpinLock() throws InterruptedException {
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Counter counter = new Counter();
        CountDownLatch doneSignal = new CountDownLatch(threadCount);
        SpinLock spinLock = new JavaSpinLock();
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                spinLock.executeSpinLock(counter);
                doneSignal.countDown();
            });
        }
        doneSignal.await();
        executorService.shutdown();
        Assertions.assertThat(counter.getCount()).isEqualTo(threadCount);
    }
}