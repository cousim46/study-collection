package rabbitmq.receiver.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitMqConfig {
    private final String DIRECT_QUEUE_NAME = "direct_queue";
    private final String DIRECT_EXCHANGE_NAME = "direct_exchange";
    private final String DIRECT_QUEUE_ROUTING_KEY = "direct_routing_key";

    @Bean
    public Queue queue() {
        return new Queue(DIRECT_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(DIRECT_QUEUE_ROUTING_KEY);
    }

}
