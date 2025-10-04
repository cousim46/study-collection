package study.ratelimiter.leaky;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import study.ratelimiter.common.CustomLock;

@Service
@RequiredArgsConstructor
public class LeakyBucketService {

    private final static String KEY = "LEAKY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final CustomLock customLock;


    public String leakyRateLimit(Integer userId, Integer requestLimitCount) {
        String result = (String)customLock.tryLock(() -> {
                ListOperations<String, Object> lists = redisTemplate.opsForList();
                Long size = lists.size(KEY);

                if (size >= (long) requestLimitCount) {
                    throw new IllegalArgumentException("허용된 요청 횟수를 초과하였습니다.");
                }
                lists.leftPush(KEY, userId);
                return String.format("%d님의 요청은 성공적으로 처리되었습니다.", userId);
            },
            5, TimeUnit.SECONDS);
        System.out.println(result);
        return result;
    }

    public void consume() {
        ListOperations<String, Object> lists = redisTemplate.opsForList();
        Long size = lists.size(KEY);
        if (size == 0) {
            System.out.println("버킷이 비어있습니다.");
            return;
        }
        Integer userId = (Integer) lists.rightPop(KEY);
        System.out.printf("현재시간 : %s, %d가 처리되었습니다.\n",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), userId);
    }
}