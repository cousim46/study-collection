package study.ratelimiter.token;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final static String PREFIX_KEY = "token";
    private final static int CAPACITY = 5;


    public void token(Long companyId, Instant now) {
        useToken(companyId, now);
        System.out.println("요청 성공~");
    }

    public void useToken(Long companyId, Instant now) {
        String key = String.format("%s:%d", PREFIX_KEY, companyId);
        Object tokenValue = redisTemplate.opsForHash()
            .get(key, "token");
        if (tokenValue == null) {
            Map<String, Object> tokenRateLimiter = new HashMap<>();
            tokenRateLimiter.put("token", 5);
            tokenRateLimiter.put("lastTokenUpdate", now.getEpochSecond());
            tokenRateLimiter.put("capacity", CAPACITY);
            redisTemplate.opsForHash().putAll(key, tokenRateLimiter);
        } else {
            Integer token = (Integer) tokenValue;
            Integer lastTokenUpdate = (Integer) redisTemplate.opsForHash()
                .get(key, "lastTokenUpdate");
            long nowEpochSecond = now.getEpochSecond();
            nowEpochSecond -= lastTokenUpdate;
            long restTime = nowEpochSecond / 60;
            if (restTime > 0) {
                int capacity = (Integer)redisTemplate.opsForHash()
                    .get(key, "capacity");
                long incrementToken = Math.min(capacity - token, restTime);
                redisTemplate.opsForHash().increment(key, "token", incrementToken);
                redisTemplate.opsForHash().put(key,"lastTokenUpdate", now);
            }else if (token == 0) {
                throw new IllegalStateException("Too many requests");
            }
            redisTemplate.opsForHash().increment(key, "token", -1);
        }
    }
}
