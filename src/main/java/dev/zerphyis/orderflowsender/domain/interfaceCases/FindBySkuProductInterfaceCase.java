package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.Optional;

public interface FindBySkuProductInterfaceCase {
    Optional<ProductResponseDto> execute(String sku);
}
