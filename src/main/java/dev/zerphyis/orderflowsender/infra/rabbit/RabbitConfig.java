package dev.zerphyis.orderflowsender.infra.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "orderflow.exchange";

    public static final String STOCK_RESERVED_QUEUE =
            "stock.reserved.queue";

    public static final String STOCK_RESERVED_ROUTING_KEY =
            "stock.reserved";

    @Bean
    public TopicExchange orderFlowExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue stockReservedQueue() {
        return QueueBuilder
                .durable(STOCK_RESERVED_QUEUE)
                .build();
    }

    @Bean
    public Binding stockReservedBinding(
            Queue stockReservedQueue,
            TopicExchange orderFlowExchange
    ) {
        return BindingBuilder
                .bind(stockReservedQueue)
                .to(orderFlowExchange)
                .with(STOCK_RESERVED_ROUTING_KEY);
    }
}
