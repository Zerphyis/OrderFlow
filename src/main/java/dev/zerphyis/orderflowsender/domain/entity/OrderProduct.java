package dev.zerphyis.orderflowsender.domain.entity;

import dev.zerphyis.orderflowsender.aplication.exceptions.order.InvalidOrderException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrderProduct {
    private UUID id;
    private UUID costumerid;
    private List<OrderItem> items;

    public OrderProduct(UUID id, UUID costumerid, List<OrderItem> items) {
        this.id = id;
        this.costumerid = costumerid;
        this.items = items;
    }

    public static OrderProduct create(
            UUID customerId,
            List<OrderItem> items
    ) {
        return new OrderProduct(
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

        items.add(item);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCostumerid() {
        return costumerid;
    }

    public void setCostumerid(UUID costumerid) {
        this.costumerid = costumerid;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
