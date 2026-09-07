package dev.zerphyis.orderflowsender.infra.persistence.repository.adapter;

import dev.zerphyis.orderflowsender.domain.entity.OrderProduct;
import dev.zerphyis.orderflowsender.domain.repository.OrderRepository;
import dev.zerphyis.orderflowsender.infra.persistence.entity.OrderJpaEntity;
import dev.zerphyis.orderflowsender.infra.persistence.mapper.OrderMapper;
import dev.zerphyis.orderflowsender.infra.persistence.repository.OrderRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderRepositoryJpa orderRepositoryJpa;
    private final OrderMapper orderMapper;

    public OrderRepositoryAdapter(OrderRepositoryJpa orderRepositoryJpa, OrderMapper orderMapper) {
        this.orderRepositoryJpa = orderRepositoryJpa;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderProduct save(OrderProduct order) {
        OrderJpaEntity entity = orderMapper.toJpaEntity(order);
        OrderJpaEntity savedEntity = orderRepositoryJpa.save(entity);
        return orderMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<OrderProduct> findById(UUID id) {
       return orderRepositoryJpa.findById(id).map(orderMapper::toDomain);
    }
}
