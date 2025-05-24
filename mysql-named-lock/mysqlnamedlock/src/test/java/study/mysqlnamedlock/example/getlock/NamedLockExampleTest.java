package study.mysqlnamedlock.example.getlock;

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
import study.mysqlnamedlock.domain.Ticket;
import study.mysqlnamedlock.domain.TicketJpaRepository;

@SpringBootTest
class NamedLockExampleTest {

    @Autowired
    private TicketJpaRepository ticketJpaRepository;
    @Autowired
    private LockExecute lockExecute;

    @BeforeEach
    void init() {
        ticketJpaRepository.save(new Ticket(1L, "여행 티켓", 100));
    }

    @AfterEach
    void clear() {
        ticketJpaRepository.deleteAllInBatch();
    }

    @DisplayName("")
    @Test
    void test() throws InterruptedException {
        //given
        Long ticketId = 1L;
        int threadCount = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    lockExecute.execute();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();

        //then
        Ticket ticket = ticketJpaRepository.findById(ticketId).get();
        Assertions.assertThat(ticket.getAmount()).isEqualTo(0);
    }
}