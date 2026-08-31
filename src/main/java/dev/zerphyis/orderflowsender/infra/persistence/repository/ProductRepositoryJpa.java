package dev.zerphyis.orderflowsender.infra.persistence.repository;

import dev.zerphyis.orderflowsender.infra.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryJpa extends JpaRepository<ProductJpaEntity, UUID> {
    Optional<ProductJpaEntity> findByIdAndActiveTrue(UUID id);

    Optional<ProductJpaEntity> findBySkuAndActiveTrue(String sku);

    List<ProductJpaEntity> findByCategoryAndActiveTrue(String category);
}

