package dev.zerphyis.orderflowsender.infra.persistence.mapper;

import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.infra.persistence.entity.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductJpaEntity toEntity(Product product){
        return  new ProductJpaEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getSku(),
                product.getStockQuantity()
        );
    }

    public Product toDomain(ProductJpaEntity productJpa) {
        return new Product(
                productJpa.getId(),
                productJpa.getName(),
                productJpa.getDescription(),
                productJpa.getCategory(),
                productJpa.getSku(),
                productJpa.getPrice(),
                productJpa.getStockQuantity()
        );
    }
}
