package transaction.outinbox.outbox.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import transaction.outinbox.outbox.event.OrderPayload;

@Component
public class RabbitMqConsumer {

    @RabbitListener(queues = {"event.queue.create.order"})
    public void orderCreate(OrderPayload orderPayload) {
        System.out.println("Received order payload: " + orderPayload);
    }
}
