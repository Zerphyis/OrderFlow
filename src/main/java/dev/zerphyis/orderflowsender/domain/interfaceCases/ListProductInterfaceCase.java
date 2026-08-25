package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.List;

public interface ListProductInterfaceCase {
    List<ProductResponseDto> execute(int page, int size);
}
