package study.ratelimiter.common;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public interface CustomLock {

    Object tryLock(Supplier<Object> supplier, int time, TimeUnit timeUnit);
}
