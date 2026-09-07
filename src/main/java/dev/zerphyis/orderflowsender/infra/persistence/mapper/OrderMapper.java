package dev.zerphyis.orderflowsender.infra.persistence.mapper;

import dev.zerphyis.orderflowsender.domain.entity.OrderItem;
import dev.zerphyis.orderflowsender.domain.entity.OrderProduct;
import dev.zerphyis.orderflowsender.infra.persistence.entity.OrderItemJpaEntity;
import dev.zerphyis.orderflowsender.infra.persistence.entity.OrderJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderJpaEntity toJpaEntity(OrderProduct order){

        OrderJpaEntity orderJpaEntity =
                new OrderJpaEntity(
                        order.getId(),
                        order.getCostumerid()
                );

        order.getItems()
                .stream()
                .map(this::toItemJpaEntity)
                .forEach(orderJpaEntity::addItem);

        return orderJpaEntity;
    }

    public OrderProduct toDomain(OrderJpaEntity entity) {

        List<OrderItem> items = entity.getItems()
                .stream()
                .map(this::toDomainItem)
                .toList();

        return new OrderProduct(
                entity.getId(),
                entity.getCustomerId(),
                items
        );
    }

    private OrderItemJpaEntity toItemJpaEntity(
            OrderItem item
    ) {
        return new OrderItemJpaEntity(
                item.getProductId(),
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity()
        );
    }

    private OrderItem toDomainItem(
            OrderItemJpaEntity entity
    ) {
        return new OrderItem(
                entity.getProductId(),
                entity.getProductName(),
                entity.getUnitPrice(),
                entity.getQuantity()
        );
    }
}
