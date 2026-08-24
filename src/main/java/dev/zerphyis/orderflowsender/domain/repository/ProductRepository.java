package dev.zerphyis.orderflowsender.domain.repository;

import dev.zerphyis.orderflowsender.domain.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product order);

    Optional<Product> findById(UUID id);

    List<Product> findAll(int offset, int size);
}
