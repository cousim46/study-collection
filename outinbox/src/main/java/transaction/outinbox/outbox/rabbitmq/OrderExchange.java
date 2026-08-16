package transaction.outinbox.outbox.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderExchange {
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("event.create.order");
    }

    @Bean
    public Queue  orderCreateQueue() {
        return new Queue("event.queue.create.order");
    }

    @Bean
    public Binding orderCreateBinding() {
        return BindingBuilder.bind(orderCreateQueue()).to(fanoutExchange());
    }

}
