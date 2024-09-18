package rabbitmq.sender.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rabbitmq.sender.message.SendMessage;

@Service
@RequiredArgsConstructor
public class FanoutExchangeService {
    private final RabbitTemplate rabbitTemplate;
    private final String DIRECT_EXCHANGE_NAME = "fanout_exchange";
    public void send() {
        SendMessage sendMessage = new SendMessage("https://hoestory.tistory.com/", "hoestory",
            "RabbitMQ");
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_NAME,null, sendMessage);
    }
}
