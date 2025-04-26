package study.lock.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {

    private int count;
    private Lock lock = new ReentrantLock();

    public void increaseCounter() {
        count += 1;
    }

    public int getCount() {
        return count;
    }
}
