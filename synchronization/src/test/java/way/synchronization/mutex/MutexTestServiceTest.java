package way.synchronization.mutex;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MutexTestServiceTest {
	@Test
	@DisplayName("동기화 기법 뮤텍스를 활용하여 원하는 결과 획득")
	public void success() throws InterruptedException {
		Mutex mutex = new Mutex();
		MutexTestService sharedData = new MutexTestService(mutex);
		Thread firstThread = new Thread(() -> {
			for (int i = 0; i < 10000000; i++) {
				sharedData.increase();
			}
		}, "첫번째 스레드");

		Thread secondThread = new Thread(() -> {
			for (int i = 0; i < 10000000; i++) {
				sharedData.increase();
			}
		}, "두번째 스레드");

		firstThread.start();
		secondThread.start();

		firstThread.join();
		secondThread.join();

		Assertions.assertThat(sharedData.getCnt()).isEqualTo(10000000 * 2);
	}
}