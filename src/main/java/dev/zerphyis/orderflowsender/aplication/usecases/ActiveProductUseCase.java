package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.ActiveProducInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.UUID;

public class ActiveProductUseCase implements ActiveProducInterfaceCase {
    private final ProductRepository repository;

    public ActiveProductUseCase(
            ProductRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public ProductResponseDto execute(UUID id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found: " + id
                ));

        product.activate();

        Product activatedProduct = repository.save(product);

        return new ProductResponseDto(
                activatedProduct.getId(),
                activatedProduct.getName(),
                activatedProduct.getDescription(),
                activatedProduct.getPrice(),
                activatedProduct.getCategory(),
                activatedProduct.getSku(),
                activatedProduct.getStockQuantity()
        );
    }
}
