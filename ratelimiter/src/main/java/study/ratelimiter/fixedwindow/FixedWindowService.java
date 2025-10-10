package study.ratelimiter.fixedwindow;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import study.ratelimiter.common.java.CustomJavaLock;

@Service
@RequiredArgsConstructor
public class FixedWindowService {

    private final static String KEY = "FIXED_WINDOW:%d";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CustomJavaLock customJavaLock;


    public String getLottoNumber(Long requestUserId) {
        return (String) customJavaLock.tryLock(() -> {
            String key = String.format(KEY, requestUserId);
            Integer requestCount = (Integer) redisTemplate.opsForValue().get(key);
            if (requestCount == null) {
                redisTemplate.opsForValue().set(key, 0, 1, TimeUnit.MINUTES);
            } else if (requestCount == 3) {
                throw new IllegalArgumentException("1분동안 요청할 수 있는 횟수를 초과하였습니다.");
            }
            redisTemplate.opsForValue().increment(key);
            Set<String> rottoNumbers = new HashSet<>();
            while (rottoNumbers.size() != 6) {
                int randomNum = (int) (Math.random() * 45) + 1;
                rottoNumbers.add(Integer.toString(randomNum));
            }
            return rottoNumbers
                .stream()
                .collect(Collectors.joining(","));
        }, 5, TimeUnit.SECONDS);
    }
}
