package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.CreateProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;

import java.util.UUID;

public class CreateProductUseCase implements CreateProductInterfaceCase {
    private final ProductRepository repository;

    public CreateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductResponseDto execute(ProductRequestDto requestDto) {

        Product product = new Product(
                UUID.randomUUID(),
                requestDto.name(),
                requestDto.description(),
                requestDto.category(),
                requestDto.sku(),
                requestDto.price(),
                requestDto.stockQuantity()
        );

        Product savedProduct = repository.save(product);

        return new ProductResponseDto(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getCategory(),
                savedProduct.getSku(),
                savedProduct.getStockQuantity()
        );
    }
}
