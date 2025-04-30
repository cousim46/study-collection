package study.lock.distrubuted.mysqlnamedlock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.lock.common.CounterRepository;

@Service
@RequiredArgsConstructor
public class MySqlNamedLock {
    private final CounterRepository counterRepository;
    private final CounterService counterService;
    private final static String MYSQL_NAME_LOCK_NAME = "counter";

    public void updateCounter() {
        String currentName = Thread.currentThread().getName();
        try {
            System.out.printf("락 시도 스레드명 : %s\n", currentName);
            counterRepository.acquireLock(MYSQL_NAME_LOCK_NAME, 2);
            System.out.printf("락 획득 스레드명 : %s\n", currentName);
            counterService.update(1L);
        }finally {
            System.out.printf("락 해제 스레드명 : %s\n", currentName);
            counterRepository.releaseLock(MYSQL_NAME_LOCK_NAME);
        }
    }
}
