package study.lock.distrubuted.mysqlnamedlock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import study.lock.common.Counter;
import study.lock.common.CounterRepository;

@Service
@RequiredArgsConstructor
public class CounterService {
    private final CounterRepository counterRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Counter update(Long counterId) {
        Counter counter = counterRepository.findById(counterId)
            .orElseThrow(IllegalArgumentException::new);
        counter.increaseCounter();
        return counter;
    }
}
