package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.ListProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.List;

public class ListProductUseCase implements ListProductInterfaceCase {

    private final ProductRepository repository;

    public ListProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductResponseDto> execute(
            int page,
            int size
    ) {
        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page cannot be negative"
            );
        }

        if (size <= 0) {
            throw new IllegalArgumentException(
                    "Size must be greater than zero"
            );
        }

        List<Product> products =
                repository.findAll(page, size);

        return products.stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getCategory(),
                        product.getSku(),
                        product.getStockQuantity()
                ))
                .toList();
    }
}