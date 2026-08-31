package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.SkuNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.FindBySkuProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.Optional;

public class FindByProductSkuUseCase implements FindBySkuProductInterfaceCase {
    private final ProductRepository repository;

    public FindByProductSkuUseCase(
            ProductRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Optional<ProductResponseDto> execute(String sku) {

        if (sku == null || sku.isBlank()) {
            throw new SkuNotFoundException(
                  sku
            );
        }

        return repository.findBySku(sku)
                .map(this::toResponse);
    }

    private ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory(),
                product.getSku(),
                product.getStockQuantity()
        );
    }

}
