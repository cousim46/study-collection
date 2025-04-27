package study.lock.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public boolean spinLock(String keyName) {
        String redisKey = getKey(keyName);
        return redisTemplate.opsForValue().setIfAbsent(redisKey, "0");
    }

    public void unlock(String ketName) {
        redisTemplate.delete(getKey(ketName));
    }

    private String getKey(String keyName) {
        return String.format("spinLock:%s", keyName);
    }
}
