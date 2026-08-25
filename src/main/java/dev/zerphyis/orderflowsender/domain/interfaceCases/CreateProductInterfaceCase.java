package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

public interface CreateProductInterfaceCase {
   ProductResponseDto execute(ProductRequestDto requestDto);
}
