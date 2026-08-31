package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.CategoryNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.FindByProductCategoryInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.List;

public class FindByProductCategoryUseCase implements FindByProductCategoryInterfaceCase {
    private final ProductRepository repository;

    public FindByProductCategoryUseCase(
            ProductRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public List<ProductResponseDto> execute(String category) {

        if (category == null || category.isBlank()) {
            throw new CategoryNotFoundException(
                    category
            );
        }

        return repository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .toList();
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
