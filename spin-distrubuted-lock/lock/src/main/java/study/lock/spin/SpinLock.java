package study.lock.spin;

import study.lock.common.Counter;

public interface SpinLock {

    void executeSpinLock(Counter counter);

}
