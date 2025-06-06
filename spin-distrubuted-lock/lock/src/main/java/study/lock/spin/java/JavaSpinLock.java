package study.lock.spin.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import study.lock.spin.SpinLock;
import study.lock.common.Counter;

public class JavaSpinLock implements SpinLock {

    private Lock lock = new ReentrantLock();

    public void executeSpinLock(Counter counter) {
        while (true) {
            boolean result = lock.tryLock();
            if (result) {
                break;
            }
            System.out.println("락 획득 실패 스레드명 : " + Thread.currentThread().getName());
        }
        try {
            System.out.println(
                "락 획득 성공 스레드명 : " + Thread.currentThread().getName());
            counter.increaseCounter();
        } finally {
            System.out.println(
                "락 해제 스레드명 : " + Thread.currentThread().getName());
            lock.unlock();
        }
    }
}
