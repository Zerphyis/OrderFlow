package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.List;

public interface FindByProductCategoryInterfaceCase {
    List<ProductResponseDto> execute(String category);
}
