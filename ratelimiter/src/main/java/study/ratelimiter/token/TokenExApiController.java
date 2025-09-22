package study.ratelimiter.token;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenExApiController {

    private final TokenService tokenService;

    @GetMapping("token/{companyId}")
    public void token(@PathVariable("companyId") Long companyId) {
        Instant now = Instant.now();
        tokenService.token(companyId,now);
    }

}
