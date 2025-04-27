package study.lock.spin.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.lock.common.Counter;
import study.lock.redis.RedisRepository;
import study.lock.spin.SpinLock;

@Service
@RequiredArgsConstructor
public class RedisSpinLock implements SpinLock {
    private final RedisRepository redisRepository;
    private final static String KEY_NAME = "counter";

    @Override
    public void executeSpinLock(Counter counter) {
        while(!redisRepository.spinLock(KEY_NAME)) {
            System.out.println("락 획득 실패 스레드명 : " + Thread.currentThread().getName());
        }
        try {
            System.out.println(
                "락 획득 성공 스레드명 : " + Thread.currentThread().getName());
            counter.increaseCounter();
        } finally {
            System.out.println(
                "락 해제 스레드명 : " + Thread.currentThread().getName());
            redisRepository.unlock(KEY_NAME);
        }
    }
}
