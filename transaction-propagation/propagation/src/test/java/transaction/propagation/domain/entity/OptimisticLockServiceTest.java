package transaction.propagation.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import transaction.propagation.domain.repository.OptimisticLockRepository;

@SpringBootTest
class OptimisticLockServiceTest {
    @Autowired
    private OptimisticLockService optimisticLockService;
    @Autowired
    private OptimisticLockRepository optimisticLockRepository;

    @BeforeEach
    void save() {
        optimisticLockRepository.save(new OptimisticLock("테스트1"));
        optimisticLockRepository.save(new OptimisticLock("테스트2"));
    }

    @DisplayName("낙관적 락 발생")
    @Test
    void occurOptimisticLock() throws InterruptedException {
        //given
        int threadCount = 2;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        try {
            for (int i = 0; i < threadCount; i++) {
                executorService.execute(() -> {
                    optimisticLockService.update(1L, "테스트");
                    System.out.println("실행됨");
                    countDownLatch.countDown();
                });
            }
        }catch (Exception e) {
            countDownLatch.await();
        }finally {
            executorService.shutdown();
        }
        Thread.sleep(500);
        OptimisticLock optimisticLock = optimisticLockRepository.findById(1L).get();
        Assertions.assertThat(optimisticLock.getName()).isEqualTo()
    }
}