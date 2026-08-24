package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.aplication.dtos.ProductResponseDto;

import java.util.List;

public interface ListProductInterfaceCase {
    List<ProductResponseDto> execute(int page, int size);
}
