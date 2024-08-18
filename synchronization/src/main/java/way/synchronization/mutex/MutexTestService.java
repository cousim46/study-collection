package way.synchronization.mutex;

import lombok.Getter;

@Getter
class MutexTestService {
	private int cnt = 0;
	private Mutex mutex;

	public MutexTestService(Mutex mutex) {
		this.mutex = mutex;
	}

	public void increase() {
		try {
			mutex.acquired();
			cnt++;
		}finally {
			mutex.release();
		}
	}
}
