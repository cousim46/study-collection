package rabbitmq.sender.exchange;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rabbitmq.sender.message.SendMessage;

@Service
@RequiredArgsConstructor
public class DirectExchangeService {

    private final RabbitTemplate rabbitTemplate;
    private final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    private final String DIRECT_QUEUE_ROUTING_KEY = "direct_routing_key2";

    public void send() {
        SendMessage sendMessage = new SendMessage("https://hoestory.tistory.com/", "hoestory",
            "RabbitMQ");
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE_NAME,DIRECT_QUEUE_ROUTING_KEY,sendMessage);
    }
}
