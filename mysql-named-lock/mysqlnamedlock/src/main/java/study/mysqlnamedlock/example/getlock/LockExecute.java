package study.mysqlnamedlock.example.getlock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LockExecute {

    private final NamedLockExample namedLockExample;
    private final TicketWriteService ticketWriteService;

    @Transactional
    public void execute() {
        namedLockExample.execute("nameLock", 2, () -> {
            ticketWriteService.decrease(1L);
            return null;
        });
    }
}

