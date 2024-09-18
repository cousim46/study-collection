package rabbitmq.sender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbitmq.sender.exchange.DirectExchangeService;
import rabbitmq.sender.exchange.FanoutExchangeService;

@RestController
@RequiredArgsConstructor
public class RabbitMQController {
    private final DirectExchangeService directExchangeService;
    private final FanoutExchangeService fanOutExchangeService;

    @GetMapping("/direct")
    public void sendDirectExchange() {
        directExchangeService.send();
    }
    @GetMapping("/fanout")
    public void sendFanoutExchange() {
        fanOutExchangeService.send();
    }
}
