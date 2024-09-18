package rabbitmq.receiver.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rabbitmq.receiver.message.SendMessage;

@Component
public class FanoutListener {

    @RabbitListener(queues = "fanout_queue")
    public void getMessage(SendMessage sendMessage){
        System.out.println(sendMessage);
    }
}
