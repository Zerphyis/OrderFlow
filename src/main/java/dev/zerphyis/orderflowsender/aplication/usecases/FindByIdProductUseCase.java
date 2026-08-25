package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.aplication.exceptions.ProductNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.FindByIdProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;

import java.util.UUID;

public class FindByIdProductUseCase implements FindByIdProductInterfaceCase {
    private final ProductRepository repository;


    public FindByIdProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public ProductResponseDto execute(UUID id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

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
