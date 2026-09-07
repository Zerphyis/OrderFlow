package dev.zerphyis.orderflowsender.domain.repository;

import dev.zerphyis.orderflowsender.domain.entity.OrderProduct;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    OrderProduct save(OrderProduct order);

    Optional<OrderProduct> findById(UUID id);
}
