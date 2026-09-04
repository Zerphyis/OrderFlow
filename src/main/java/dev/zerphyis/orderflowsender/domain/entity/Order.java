package dev.zerphyis.orderflowsender.domain.entity;

import dev.zerphyis.orderflowsender.aplication.exceptions.order.InvalidOrderException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private UUID id;
    private  UUID custumerId;
    private List<OrderItem> itens;

    public Order(UUID id, UUID custumerId, List<OrderItem> itens) {
        this.id = id;
        this.custumerId = custumerId;
        this.itens = itens;
    }

    public static Order create(
            UUID customerId,
            List<OrderItem> items
    ) {
        return new Order(
                UUID.randomUUID(),
                customerId,
                items
        );
    }

    private void validate(
            UUID customerId,
            List<OrderItem> items
    ) {
        if (Objects.isNull(customerId)) {
            throw new InvalidOrderException("Customer id must not be null");
        }

        if (Objects.isNull(items) || items.isEmpty()) {
            throw new InvalidOrderException(
                    "Order must contain at least one item"
            );
        }

        if (items.stream().anyMatch(Objects::isNull)) {
            throw new InvalidOrderException(
                    "Order items must not contain null values"
            );
        }
    }

    public void addItem(OrderItem item) {
        if (Objects.isNull(item)) {
            throw new InvalidOrderException(
                    "Order item must not be null"
            );
        }

        itens.add(item);
    }

}
