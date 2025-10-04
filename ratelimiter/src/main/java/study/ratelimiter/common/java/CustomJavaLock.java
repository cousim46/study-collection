package study.ratelimiter.common.java;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import study.ratelimiter.common.CustomLock;

@Component("javaLock")
public class CustomJavaLock implements CustomLock {

    private final static Lock lock = new ReentrantLock();

    @Override
    public Object tryLock(Supplier<Object> supplier, int time, TimeUnit timeUnit) {
        try {
            boolean isLock = lock.tryLock(time, timeUnit);
            if(!isLock) {
                throw new IllegalArgumentException("요청 횟수 초과");
            }
            try {
                return supplier.get();
            }finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
