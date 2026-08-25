package dev.zerphyis.orderflowsender.infra.persistence.repository;

import dev.zerphyis.orderflowsender.infra.persistence.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryJpa extends JpaRepository<ProductJpaEntity, UUID> {
    Optional<ProductJpaEntity> findByIdAndActiveTrue(UUID id);

    List<ProductJpaEntity> findAllByActiveTrue(Pageable pageable);
}
