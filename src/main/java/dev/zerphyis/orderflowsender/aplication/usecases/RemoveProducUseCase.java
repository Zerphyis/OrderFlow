package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.ProductNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.interfaceCases.RemoveProductInterfaceCase;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;

import java.util.UUID;

public class RemoveProducUseCase implements RemoveProductInterfaceCase {
    private final ProductRepository productRepository;

    public RemoveProducUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)
                );

        product.deactivate();

        productRepository.save(product);
    }
}
