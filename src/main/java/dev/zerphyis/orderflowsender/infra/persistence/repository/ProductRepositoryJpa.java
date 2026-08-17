package dev.zerphyis.orderflowsender.infra.persistence.repository;

import dev.zerphyis.orderflowsender.infra.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepositoryJpa extends JpaRepository<ProductJpaEntity, UUID> {
}
