package transaction.outinbox.outbox;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transaction.outinbox.outbox.dto.OrderCreateRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    public void save(@RequestBody OrderCreateRequest orderCreateRequest){
        orderService.save(orderCreateRequest);
    }
}
