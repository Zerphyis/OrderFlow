package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
import dev.zerphyis.orderflowsender.aplication.exceptions.ProductNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.UpdateProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;

import java.util.UUID;

public class UpdateProductUseCase implements UpdateProductInterfaceCase {
    private final ProductRepository repository;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductResponseDto execute(
            UUID id,
            UpdateProductRequestDto requestDto
    ) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());
        product.setCategory(requestDto.category());
        product.setSku(requestDto.sku());

        Product updatedProduct = repository.save(product);

        return new ProductResponseDto(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getCategory(),
                updatedProduct.getSku(),
                updatedProduct.getStockQuantity()
        );
    }
}
