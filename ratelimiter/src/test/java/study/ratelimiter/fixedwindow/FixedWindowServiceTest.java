package study.ratelimiter.fixedwindow;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FixedWindowServiceTest {

    @Autowired
    private FixedWindowService fixedWindowService;

    @DisplayName("지정한 시간의 허용된 요청 횟수를 초과하면 예외가 발생한다.")
    @Test
    void checkRequestLimitOrThrow() throws InterruptedException {
        Long requestId = 2L;
        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    fixedWindowService.getLottoNumber(requestId);
                }catch (IllegalArgumentException e) {
                    throw e;
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

    }
}