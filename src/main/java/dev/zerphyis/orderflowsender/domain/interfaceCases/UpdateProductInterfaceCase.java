package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;

import java.util.UUID;

public interface UpdateProductInterfaceCase {
    ProductResponseDto execute(UUID id, UpdateProductRequestDto requestDto);
}
