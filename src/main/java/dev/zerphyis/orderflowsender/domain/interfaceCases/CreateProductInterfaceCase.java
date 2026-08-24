package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.aplication.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.aplication.dtos.ProductResponseDto;

public interface CreateProductInterfaceCase {
   ProductResponseDto execute(ProductRequestDto requestDto);
}
