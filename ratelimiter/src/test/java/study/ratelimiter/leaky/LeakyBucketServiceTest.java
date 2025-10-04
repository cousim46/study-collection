package study.ratelimiter.leaky;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeakyBucketServiceTest {

    @Autowired
    private LeakyBucketService leakyBucketService;

    @DisplayName("허용된 요청 횟수를 초과하면 예외가 발생한다.")
    @Test
    void checkRequestLimitOrThrow() throws InterruptedException {
        final int requestLimitCount = 10;
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        List<Integer> userIds = getUserId(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executorService.execute(() -> {
                try {
                    leakyBucketService.leakyRateLimit(userIds.get(index), requestLimitCount);
                }catch (Exception e) {
                    throw e;
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    private List<Integer> getUserId(int threadCount) {
        List<Integer> userIds = new ArrayList<>();
        for (int i = 1; i <= threadCount; i++) {
            userIds.add(i);
        }
        return userIds;

    }
}