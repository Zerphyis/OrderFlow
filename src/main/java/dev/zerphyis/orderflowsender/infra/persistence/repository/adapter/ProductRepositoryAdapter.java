package dev.zerphyis.orderflowsender.infra.persistence.repository.adapter;

import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.persistence.entity.ProductJpaEntity;
import dev.zerphyis.orderflowsender.infra.persistence.mapper.ProductMapper;
import dev.zerphyis.orderflowsender.infra.persistence.repository.ProductRepositoryJpa;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductRepositoryJpa repository;
    private final ProductMapper mapper;

    public ProductRepositoryAdapter(ProductRepositoryJpa repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {

        ProductJpaEntity entity = mapper.toEntity(product);

        ProductJpaEntity saved = repository.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(UUID id) {

        return repository.findByIdAndActiveTrue(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return repository.findAll(pageable).getContent().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        return repository
                .findBySkuAndActiveTrue(sku)
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return repository
                .findByCategoryAndActiveTrue(category)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}