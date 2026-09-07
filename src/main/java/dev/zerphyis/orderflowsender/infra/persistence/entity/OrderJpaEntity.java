package dev.zerphyis.orderflowsender.infra.persistence.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private UUID id;
    private UUID costumerId;

    @OneToMany(
    mappedBy ="order",
    cascade = CascadeType.ALL,
    orphanRemoval =true
            )
    private List<OrderItemJpaEntity> items = new ArrayList<>();

    protected OrderJpaEntity() {
    }

    public OrderJpaEntity(UUID id, UUID costumerId) {
        this.id = id;
        this.costumerId = costumerId;
    }

    public void addItem(OrderItemJpaEntity item) {
        item.setOrder(this);
        this.items.add(item);
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return costumerId;
    }

    public List<OrderItemJpaEntity> getItems() {
        return items;
    }
}
