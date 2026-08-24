package dev.zerphyis.orderflowsender.infra.persistence.repository.adapter;

import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.persistence.entity.ProductJpaEntity;
import dev.zerphyis.orderflowsender.infra.persistence.mapper.ProductMapper;
import dev.zerphyis.orderflowsender.infra.persistence.repository.ProductRepositoryJpa;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductRepositoryAdapter  implements ProductRepository {
    private final ProductRepositoryJpa repositoryJpa;
    private final ProductMapper productMapper;

    public ProductRepositoryAdapter(ProductRepositoryJpa repositoryJpa, ProductMapper productMapper) {
        this.repositoryJpa = repositoryJpa;
        this.productMapper = productMapper;
    }


    @Override
    public Product save(Product product) {
        ProductJpaEntity entity=productMapper.toEntity(product);
        ProductJpaEntity savedEntity=repositoryJpa.save(entity);
        return  productMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repositoryJpa.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public List<Product> findAll(int offset, int size) {
        return repositoryJpa.findAll()
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }
}
