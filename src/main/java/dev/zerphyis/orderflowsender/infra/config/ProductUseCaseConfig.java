package dev.zerphyis.orderflowsender.infra.config;

import dev.zerphyis.orderflowsender.aplication.usecases.*;
import dev.zerphyis.orderflowsender.domain.interfaceCases.*;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig {

    @Bean
    public CreateProductInterfaceCase createProductInterfaceCase(ProductRepository repository) {
        return new CreateProductUseCase(repository);
    }

    @Bean
    public UpdateProductInterfaceCase updateProductInterfaceCase(ProductRepository repository) {
        return new UpdateProductUseCase(repository);
    }

    @Bean
    public RemoveProductInterfaceCase removeProductInterfaceCase(ProductRepository repository) {
        return new RemoveProducUseCase(repository);
    }

    @Bean
    public ListProductInterfaceCase listProductInterfaceCase(ProductRepository repository) {
        return new ListProductUseCase(repository);
    }

    @Bean
    public FindByIdProductInterfaceCase findByIdProductInterfaceCase(ProductRepository repository) {
        return new FindByIdProductUseCase(repository);
    }

    @Bean
    public FindByProductCategoryInterfaceCase findProductsByCategoryInterfaceCase(
            ProductRepository repository
    ) {
        return new FindByProductCategoryUseCase(repository);
    }

    @Bean
    public FindBySkuProductInterfaceCase findProductBySkuInterfaceCase(
            ProductRepository repository
    ) {
        return new FindByProductSkuUseCase(repository);
    }

    @Bean
    public ActiveProducInterfaceCase activateProductInterfaceCase(
            ProductRepository repository
    ) {
        return new ActiveProductUseCase(repository);
    }
}