package dev.zerphyis.orderflowsender.infra.persistence.repository;

import dev.zerphyis.orderflowsender.infra.persistence.entity.OrderJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepositoryJpa extends JpaRepository<OrderJpaEntity, UUID> {
}
