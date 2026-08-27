package dev.zerphyis.orderflowsender.infra.config;

import dev.zerphyis.orderflowsender.aplication.usecases.*;
import dev.zerphyis.orderflowsender.domain.interfaceCases.*;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig {

    @Bean
    public CreateProductInterfaceCase createProductInterfaceCase(ProductRepository repository){
        return new CreateProductUseCase(repository);
    }

    @Bean
    public FindByIdProductInterfaceCase findByIdProductUseCase(ProductRepository repository){
        return new FindByIdProductUseCase(repository);
    }

    @Bean
    public ListProductInterfaceCase listProductInterfaceCase(ProductRepository repository){
        return new ListProductUseCase(repository);
    }

    @Bean
    public UpdateProductInterfaceCase UpdateProductInterfaceCase(ProductRepository repository){
        return new UpdateProductUseCase(repository);
    }

    @Bean
    public RemoveProductInterfaceCase RemoveProductInterfaceCase(ProductRepository repository){
        return new RemoveProducUseCase(repository);
    }
}
