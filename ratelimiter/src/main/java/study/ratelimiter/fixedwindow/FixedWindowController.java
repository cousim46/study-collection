package study.ratelimiter.fixedwindow;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FixedWindowController {

    private final FixedWindowService fixedWindowService;

//    @GetMapping("/fixed")
//    public Set<Integer> getRandomNum(Long requestUserId) {
//        return fixedWindowService.getLottoNumber(requestUserId);
//    }

}
