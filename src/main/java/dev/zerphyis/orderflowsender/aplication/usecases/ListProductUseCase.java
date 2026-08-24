package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.ListProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;

import java.util.List;

public class ListProductUseCase implements ListProductInterfaceCase {
    private final ProductRepository repository;

    public ListProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<ProductResponseDto> execute(int page, int size) {
        int offset = page * size;
        List<Product> products = repository.findAll(offset, size);


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
