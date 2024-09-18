# 뮤텍스(Mutex)
- 뮤텍스 또는 상호배제는 공유 자원에 대한 경쟁상태를 방지하고 동시성 제어를 위한 락 메커니즘입니다.
- 임계 영역에는 하나의 스레드만 락을 획득하여 접근이 가능하고 락을 획득하지 못한 스레드는 대기상태에 있습니다.
-  락 획득한 스레드만 락을 해제할 수 있고 다른 스레드가 해당 락을 해제를 하지 못합니다.

![img.png](img.png)